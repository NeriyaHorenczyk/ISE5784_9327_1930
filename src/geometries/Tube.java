package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Tube class that inherit from RadialGeometry class.
 */
public class Tube extends RadialGeometry
{
	protected Ray axis;

	/**
	 * parameter constructor that builds a new tube with the given parameter as radius and axis.
	 *
	 * @param radius the given radius parameter that initialize the new tube's radius.
	 * @param axis   the given axis parameter that initialize the new tube's axis.
	 */
	public Tube(double radius, Ray axis)
	{
		super(radius);
		this.axis = axis;
	}

	@Override
	public Vector getNormal(Point p)
	{
		Vector v = p.subtract(axis.getHead());
		double t = axis.getDirection().dotProduct(v);

		if(isZero(t))
			return v.normalize();

		return v.subtract(axis.getDirection().scale(t)).normalize();
	}


	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
	{
		return null;
	}
}
