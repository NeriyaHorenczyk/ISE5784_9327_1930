package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Cylinder class that inherit from Tube class.
 */
public class Cylinder extends Tube
{
	protected final double height;

	/**
	 * parameter constructor that build a new cylinder with the given parameter.
	 *
	 * @param radius the parameter radius that represent the new cylinder's radius.
	 * @param axis   the parameter axis that represent the new cylinder's axis.
	 * @param h      the parameter height that represent the new cylinder's height.
	 */
	public Cylinder(double radius, Ray axis, double h)
	{
		super(radius, axis);
		this.height = h;
	}

	@Override
	public Vector getNormal(Point p)
	{
		if(super.axis.getHead().equals(p))
			return super.axis.getDirection();


		Vector v = p.subtract(axis.getHead());
		double t = alignZero(Math.abs(axis.getDirection().dotProduct(v)));

		if (alignZero(height - t) == 0 || isZero(t))
		{
			return super.axis.getDirection();
		}

		if (alignZero(height - t) > 0)
		{
			return super.getNormal(p);
		}

		return null;
	}
}
