package renderer;

import geometries.Geometry;
import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;
import java.util.Map;

import static geometries.Intersectable.GeoPoint;
import static java.lang.Math.abs;
import static primitives.Util.alignZero;
import static primitives.Util.compareSign;

/**
 * A simple ray tracer
 */
public class SimpleRayTracer extends RayTracerBase {
	/**
	 * The maximum level of recursion
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	/**
	 * The minimum value of k to calculate the color
	 */
	private static final double MIN_CALC_COLOR_K = 0.001;
	/**
	 * The initial value of k
	 */
	private static final double INITIAL_K = 1;

	/**
	 * Constructor
	 *
	 * @param scene the scene to trace rays in
	 */
	public SimpleRayTracer(Scene scene) {
		super(scene);
	}

	@Override
	public Color traceRay(Ray ray) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
	}

	@Override
	public Color traceRay(List<Ray> rays) {
		Color color = new Color(0, 0, 0);

		// Trace all rays and sum up their colors
		for (Ray ray : rays) {
			List<GeoPoint> intersectionPoints = scene.geometries.findGeoIntersections(ray);
			if (intersectionPoints==null)
				color = scene.background;
			else
			{
				GeoPoint closestPoint = ray.findClosestGeoPoint(intersectionPoints);
				color = color.add(calcColor(closestPoint, ray));
			}
		}

		// Average the color
		return color.reduce(rays.size());
	}

	/**
	 * Calculate the color at a point
	 *
	 * @param gp  the point to calculate the color for
	 * @param ray the ray that hit the point
	 * @return the color at the point
	 */
	private Color calcColor(GeoPoint gp, Ray ray) {
		return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)).add(scene.ambientLight.getIntensity());
	}

	/**
	 * Calculate the color at a point
	 *
	 * @param intersection the point
	 * @param ray          the ray that hit the point
	 * @param level        the level of the recursion
	 * @param k            the k value of the point
	 * @return the color at the point
	 */
	private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(intersection, ray, k);
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
	}

	/**
	 * Find the closest intersection of a ray with the scene
	 *
	 * @param ray the ray
	 * @return the closest intersection
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
	}

	/**
	 * Construct the reflected ray
	 *
	 * @param gp        the point to reflect
	 * @param direction the direction of the ray
	 * @param n         the normal at the point
	 * @return the reflected ray
	 */
	private Ray constructReflectedRay(GeoPoint gp, Vector direction, Vector n) {
		return new Ray(gp.point, direction.subtract(n.scale(direction.dotProduct(n) * 2)), n);
	}

	/**
	 * Construct the refracted ray
	 *
	 * @param gp        the point to refract
	 * @param direction the direction of the ray
	 * @param normal    the normal at the point
	 * @return the refracted ray
	 */
	private Ray constructRefractedRay(GeoPoint gp, Vector direction, Vector normal) {
		return new Ray(gp.point, direction, normal);
	}

	/**
	 * Calculate the global effects at a point
	 *
	 * @param gp    the point
	 * @param ray   the ray
	 * @param level the level of the recursion
	 * @param k     the k value of the point
	 * @return the color at the point
	 */
	private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
		Vector v = ray.getDirection();
		Vector n = gp.geometry.getNormal(gp.point);
		Material material = gp.geometry.getMaterial();

		Double3 kkr = k.product(material.kR);
		Double3 kkt = k.product(material.kT);

		if (!(kkr.lowerThan(MIN_CALC_COLOR_K)))
			return calcGlobalEffects(constructReflectedRay(gp, v, n), level, material.kR, kkr);

		if (!(kkt.lowerThan(MIN_CALC_COLOR_K)))
			return calcGlobalEffects(constructRefractedRay(gp, v, n), level, material.kT, kkt);

		return Color.BLACK;
	}

	/**
	 * Calculate the global effects at a point
	 *
	 * @param ray   the ray to calculate the color for
	 * @param level the level of the recursion
	 * @param k     the color at the point
	 * @param kx    the color of the effect
	 * @return the color at the point
	 */
	private Color calcGlobalEffects(Ray ray, int level, Double3 k, Double3 kx) {
		Double3 kkx = k.product(kx);
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;
		GeoPoint gp = findClosestIntersection(ray);
		return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kx).scale(k));
	}

	/**
	 * Calculate the local effects at a point
	 *
	 * @param gp  the point
	 * @param ray the ray
	 * @param k   the k value of the point
	 * @return the color at the point
	 */
	private Color calcLocalEffects(Intersectable.GeoPoint gp, Ray ray, Double3 k) {
		Color color = gp.geometry.getEmission();

		Vector n = gp.geometry.getNormal(gp.point);
		Vector v = ray.getDirection();
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0) return color;
		Material material = gp.geometry.getMaterial();

		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(gp.point);
			double nl = alignZero(n.dotProduct(l));
			//if (Util.compareSign(nl, nv) && unshaded(gp, l, n, lightSource)) {
			if (compareSign(nl, nv)) {
				Double3 ktr = transparency(gp, l, n, lightSource);
				if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
					Color iL = lightSource.getIntensity(gp.point).scale(ktr);
					color = color.add(iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
				}
			}
		}
		return color;
	}

	/**
	 * Calculate the transparency of the point
	 *
	 * @param gp    the point
	 * @param l     the light vector
	 * @param n     the normal at the point
	 * @param light the light source
	 * @return the transparency of the point
	 */
	private Double3 transparency(GeoPoint gp, Vector l, Vector n, LightSource light) {
		Ray lightRay = new Ray(gp.point, l.scale(-1), n); // from point to light source
		var intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
		Double3 ktr = Double3.ONE;

		if (intersections == null)
			return ktr;

		for (GeoPoint p : intersections) {
			ktr = ktr.product(p.geometry.getMaterial().kT);
			if (ktr.lowerThan(MIN_CALC_COLOR_K))
				return Double3.ZERO;
		}

		return ktr;
	}

	/**
	 * Check if the point is shaded
	 *
	 * @param gp          the point
	 * @param l           the light vector
	 * @param n           the normal at the point
	 * @param lightSource the light source
	 * @return true if the point is shaded, false otherwise
	 */
	@SuppressWarnings("unused")
	@Deprecated(forRemoval = true)
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		var intersections = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(gp.point));
		if (intersections == null)
			return true;
		for (GeoPoint p : intersections)
			if (!p.geometry.getMaterial().kT.equals(Double3.ZERO))
				return false;
		return true;
	}

	/**
	 * Calculate the diffusive effect at a point
	 *
	 * @param material the material at the point
	 * @param nl       the dot product of the normal and the light vector
	 * @return the diffusive effect at the point
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(abs(nl));
	}

	/**
	 * Calculate the specular effect at a point
	 *
	 * @param material the material at the point
	 * @param n        the normal at the point
	 * @param l        the light vector at the point
	 * @param nl       the dot product of the normal and the light vector
	 * @param v        the view vector at the point
	 * @return the specular effect at the point
	 */
	private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
		Vector reflectVector = l.subtract(n.scale(nl * 2));
		double minusVR = -alignZero(v.dotProduct(reflectVector));
		return minusVR <= 0 ? Double3.ZERO : material.kS.scale(Math.pow(minusVR, material.shininess));
	}
}