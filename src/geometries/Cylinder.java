package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder class that inherit from Tube class.
 */
public class Cylinder extends Tube
{
	private double height;

	/**
	 * parameter constructor that build a new cylinder with the given parameter.
	 * @param radius the parameter radius that represent the new cylinder's radius.
	 * @param axis the parameter axis that represent the new cylinder's axis.
	 * @param height the parameter height that represent the new cylinder's height.
	 */
	public Cylinder(double radius, Ray axis, double height)
	{
		super(radius, axis);
		this.height = height;
	}

	@Override
	public Vector getNormal(Point p)
	{
		return null;
	}
}
