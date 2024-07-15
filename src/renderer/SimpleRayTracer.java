package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

public class SimpleRayTracer extends RayTracerBase
{

	private static final double DELTA = 0.1;

	/**
	 * The maximum level of recursion for the recursive ray tracing algorithm.
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;

	/**
	 * The minimum value of k for the recursive ray tracing algorithm.
	 */
	private static final double MIN_CALC_COLOR_K = 0.001;

	//-----------------------------constructor-------------------------

	/**
	 * Creates a new instance of the SimpleRayTracer class with the specified scene.
	 *
	 * @param scene The scene object containing information about the scene to be rendered.
	 */
	public SimpleRayTracer(Scene scene)
	{
		super(scene);
	}

	/**
	 * Checks if the point is shaded by another geometry in the scene.
	 *
	 * @param gp The point to check if it is shaded.
	 * @param l  The vector from the point to the light source.
	 * @param n  The normal vector at the point.
	 * @return True if the point is not shaded by another geometry, false otherwise.
	 */

	private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource ls)
	{
		Vector lightDirection = l.scale(-1);// from point to light source
		//Ray lightRay = new Ray(gp.point, lightDirection);
		Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
		Point point = gp.point.add(delta);
		Ray shadowRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(shadowRay, ls.getDistance(point));
		return gp.geometry.getMaterial().kT.equals(Double3.ZERO) && intersections == null;
	}


	/**
	 * Calculate the color of a given point with respective to her geometry
	 * using the phong model
	 *
	 * @param gp  the geo point to calculate the color for
	 * @param ray the direction for the phong model
	 * @return the color of the point
	 */
	private Color calcColor(GeoPoint gp, Ray ray)
	{
		return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE)
				.add(scene.ambientLight.getIntensity());
	}

	/**
	 * The method returns the color in a point with given material and considering reflection and refraction.
	 * Calculate by the phong model.
	 *
	 * @param gp    the point to color
	 * @param ray   in the phong model
	 * @param level of reflection and refraction
	 * @param k     how much effect will the global effect have
	 * @return the color in the point
	 */
	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k)
	{
		Color color = calcLocalEffects(gp, ray, k);
		return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
	}


	/**
	 * Calculating the global effects on the point which is reflection and refraction
	 *
	 * @param gp    the point and it's geometry
	 * @param ray   the ray that intersects in the point
	 * @param level the level of reflection and refraction
	 * @param k     how much refraction
	 * @return the color after considering the global effect
	 */
	private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k)
	{
		Material material = gp.geometry.getMaterial();
		return calcGlobalEffect(constructReflectedRay(gp, ray), level, k, material.kT)
				.add(calcGlobalEffect(constructReflectedRay(gp, ray), level, k, material.kR));
	}

	/**
	 * Calculating refraction or reflection on point
	 *
	 * @param ray   the refracted or reflected ray
	 * @param level the level of reflection and refraction
	 * @param k     how much effect will the global effect have
	 * @param kX    kT or kR
	 * @return the effect of refraction or reflection
	 */
	private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kX)
	{
		Double3 kKx = kX.product(k.equals(new Double3(MIN_CALC_COLOR_K)) ? Double3.ONE : k);
		if (kKx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;
		GeoPoint gp = findClosestIntersection(ray);
		return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kKx)).scale(kX);
	}


	/**
	 * Calculating the local effects on the point and returning its color
	 *
	 * @param gp  the point and it's geometry
	 * @param ray the ray for the phong model
	 * @param k   how much effect will the global effect have
	 * @return the color on the point
	 */

	private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k)
	{
		Vector n = gp.geometry.getNormal(gp.point);
		Vector v = ray.getDirection();
		Color color = gp.geometry.getEmission();
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0) return color;
		return color;
	}

	/**
	 * Finds the closest intersection GeoPoint to the ray's head
	 *
	 * @param ray the ray
	 * @return the closest GeoPoint to the ray's head
	 */
	private GeoPoint findClosestIntersection(Ray ray)
	{
		var intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null) return null;
		return ray.findClosestGeoPoint(intersections);
	}


	/**
	 * Calculates the diffusive effect
	 *
	 * @param material the material of the shape
	 * @param nl       the dot product between the normal and the vector from the light source
	 * @return the diffusive effect
	 */
	private Double3 calcDiffusive(Material material, double nl)
	{
		return material.kD.scale(Math.abs(nl));
	}


	/**
	 * Calculates the specular effect on the point
	 *
	 * @param material the material of the shape
	 * @param n        the normal in the point
	 * @param l        the vector from the light source
	 * @param nl       l dot product n
	 * @param v        the direction f the vector
	 * @return the specular effect
	 */
	private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v)
	{
		double minusRV = -alignZero(l.subtract(n.scale(2 * nl)).dotProduct(v));
		return minusRV <= 0 ? Double3.ZERO : material.kS.scale(Math.pow(minusRV, material.shininess));
	}

	/**
	 * The function check if what is the transparency
	 *
	 * @param gp    the point for shading or not
	 * @param light the light
	 * @param n     the normal in the point
	 * @param l     the vector from the point to the light
	 * @return the level of transparency
	 */
	private Double3 transparency(GeoPoint gp, LightSource light,
	                             Vector n, Vector l)
	{
		Ray ray = new Ray(gp.point, l.scale(-1), n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray, light.getDistance(gp.point));
		Double3 ktr = Double3.ONE;

		if (intersections != null)
		{
			for (GeoPoint intersection : intersections)
			{
				ktr = ktr.product(intersection.geometry.getMaterial().kT);
				if (ktr.lowerThan(MIN_CALC_COLOR_K))
					return Double3.ZERO;
			}
		}
		return ktr;
	}

	/**
	 * Creates the reflected ray
	 *
	 * @param geoPoint the point from which the ray is going
	 * @param ray      the ray that intersects the point
	 * @return the reflected ray
	 */
	private Ray constructReflectedRay(GeoPoint geoPoint, Ray ray)
	{
		Vector v = ray.getDirection();
		Vector n = geoPoint.geometry.getNormal(geoPoint.point);
		double nv = alignZero(n.dotProduct(v));
		Vector r = v.subtract(n.scale(2 * nv));
		return new Ray(geoPoint.point, r, n);
	}


	//---------------------------override functions-------------------------
	@Override
	public Color traceRay(Ray ray)
	{
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background
				: calcColor(closestPoint, ray);
	}

}