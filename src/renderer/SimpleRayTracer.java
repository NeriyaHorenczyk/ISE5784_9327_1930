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
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(shadowRay,ls.getDistance(point));
		return intersections == null;
	}

	/**



	 */
	private Color calcColor(GeoPoint geoPoint, Ray ray)
	{
		return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission())
				.add(calcLocalEffects(geoPoint, ray));
	}

	/**
	 * Calculates the effect of different light sources on a point in the scene
	 * according to the Phong model.
	 *
	 * @param intersection The point on the geometry in the scene.
	 * @param ray          The ray from the camera to the intersection.
	 * @return The color of the point affected by local light sources.
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray)
	{
		Vector v = ray.getDirection();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;



		Double3 kd = intersection.geometry.getMaterial().kD, ks = intersection.geometry.getMaterial().kS;

		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights)
		{
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));

			if ((nl * nv > 0) && unshaded(intersection, l, n, lightSource))
			{ // sign(nl) == sign(nv) //checks if light direction and camera direction is the same
				Color lightIntensity = lightSource.getIntensity(intersection.point);
				color = color.add(lightIntensity.scale(calcDiffuse(kd, nl).add(
						calcSpecular(intersection.geometry.getMaterial(), n,l, nl, v))));
			}
		}
		return color;
	}

	/**

	 */
	private Double3 calcDiffuse(Double3 kd, double nl)
	{
		return (kd.scale(Math.abs(nl)));
	}


	/**


	 */
	private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
		Double3 ks = material.kS;
		double vr = alignZero(v.scale(-1).dotProduct(l.subtract(n.scale(2 * nl)).normalize()));
		vr = (vr > 0) ? vr : 0; // Ensure vr is non-negative
		if (vr <= 0) return Double3.ZERO; // No specular reflection in this case

		double result = 1.0;
		for (int i = 0; i < material.shininess; i++) {
			result *= vr;
		}

		return ks.scale(result);
	}
//	private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess,
//	                           Color lightIntensity)
//	{
//		Vector r = l.add(n.scale(-2 * nl)); // nl must not be zero!
//		double minusVR = -alignZero(r.dotProduct(v));
//		if (minusVR <= 0)
//		{
//			return new primitives.Color(Color.BLACK.getColor()); // View from direction opposite to r vector
//		}
//		return lightIntensity.scale(ks.scale(Math.pow(minusVR, nShininess)));
//	}


	//---------------------------override functions-------------------------
	@Override
	public Color traceRay(Ray ray)
	{

		List<GeoPoint> geoPoints = scene.geometries.findGeoIntersections(ray);

		if (geoPoints == null)
			return scene.background;

		return calcColor(ray.findClosestGeoPoint(geoPoints), ray);
	}
}