package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Sphere class that inherit from RadialGeometry class.
 */
public class Sphere extends RadialGeometry
{
	private final Point center;

	/**
	 * parameter constructor that builds a new sphere with the given parameter as radius and center.
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
	public List<Point> findIntersections(Ray ray)
	{

		return null;
	}
}
