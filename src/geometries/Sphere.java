package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Sphere class that inherit from RadialGeometry class.
 */
public class Sphere extends RadialGeometry
{
	private final Point center;

	/**
	 * parameter constructor that builds a new sphere with the given parameter as radius and center.
	 *
	 * @param radius the given radius parameter that initialize the new sphere's radius.
	 * @param center the given center parameter that initialize the new sphere's center.
	 */
	public Sphere(double radius, Point center)
	{
		super(radius);
		this.center = center;
	}

	@Override
	public Vector getNormal(Point p)
	{

		return p.subtract(center).normalize();
	}


	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
	{
		Point P0 = ray.getHead();
		Vector v = ray.getDirection();
		if (P0.equals(center))
		{
			return List.of(new GeoPoint(this, ray.getPoint(radius)));
		}
		Vector u = center.subtract(P0);
		double tm = v.dotProduct(u);
		double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
		if (d >= radius)
		{
			return null;
		}
		double th = alignZero(Math.sqrt(radius * radius - d * d));
		double t1 = alignZero(tm + th);
		double t2 = alignZero(tm - th);
		if (t1 > 0 && t2 > 0 && alignZero(maxDistance - t1) > 0)
		{
			Point P1 = P0.add(v.scale(t1));
			Point P2 = P0.add(v.scale(t2));
			return List.of(new GeoPoint(this, P1), new GeoPoint(this, P2));

		}
		if (t1 > 0 && t2 > 0 && alignZero(maxDistance - t2) > 0)
		{
			Point P2 = ray.getPoint(t2);
			return List.of(new GeoPoint(this, P2));

		}
		if (t1 > 0 && t2 > 0)
		{
			return null;

		}
		if (t1 > 0 && alignZero(maxDistance - t1) > 0)
		{
			Point P1 = ray.getPoint(t1);
			return List.of(new GeoPoint(this, P1));
		}


		return null;

	}

}
