package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable interface represents all the geometric shapes that can be intersected by a ray.
 */
public abstract class Intersectable
{

	/**
	 * GeoPoint class that represents a point on the geometry shape.
	 */
	public static class GeoPoint
	{
		/**
		 * The geometry shape.
		 */
		public Geometry geometry;

		/**
		 * The point on the geometry shape.
		 */
		public Point point;

		/**
		 * parameter constructor that builds a new GeoPoint with the given geometry and point.
		 *
		 * @param geometry the given geometry parameter that initialize the new GeoPoint's geometry.
		 * @param point    the given point parameter that initialize the new GeoPoint's point.
		 */
		public GeoPoint(Geometry geometry, Point point)
		{
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;

			return obj instanceof GeoPoint other
					&& this.geometry.equals(other.geometry)
					&& this.point.equals(other.point);
		}

		@Override
		public String toString()
		{
			return "GeoPoint [geometry=" + geometry.toString() + ", point=" + point.toString() + "]";
		}
	}


	/**
	 * findIntersections function that finds all the intersections of the given ray with the shape.
	 *
	 * @param ray the given ray parameter
	 * @return list of all the intersections points of the ray with the shape.
	 */
	public List<Point> findIntersections(Ray ray)
	{
		return findIntersections(ray,Double.POSITIVE_INFINITY);
	}

	/**
	 *


	 */
	public List<Point> findIntersections(Ray ray,double maxDistance)
	{
		var geoList = findGeoIntersections(ray,maxDistance);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}
//	/**
//	 * findGeoIntersections function that finds all the intersections of the given ray with the shape.
//	 * using the helper function
//	 * @param ray the given ray parameter
//	 * @return list of all the intersections points of the ray with the shape.
//	 */
//	public final List<GeoPoint> findGeoIntersections(Ray ray)
//	{
//		return findGeoIntesectionsHelper(ray);
//	}
//
//	/**
//	 * findGeoIntesectionsHelper function that finds all the intersections of the given ray with the shape.
//	 * @param ray the given ray parameter
//	 * @return list of all the intersections points of the ray with the shape.
//	 */
//	protected abstract List<GeoPoint> findGeoIntesectionsHelper(Ray ray);

	public final List<GeoPoint> findGeoIntersections(Ray ray)
	{
		return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
	}

	public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance)
	{
		return findGeoIntersectionsHelper(ray, maxDistance);
	}
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

}
