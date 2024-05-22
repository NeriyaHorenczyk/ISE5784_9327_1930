package primitives;

/**
 * Point class that represent a three values Point in space.
 */
public class Point
{

	protected final Double3 xyz;

	/**
	 * This is a static point that represent the (0,0,0) point.
	 */
	public static Point ZERO = new Point(Double3.ZERO);

	/**
	 * Double3 parameter constructor that builds a new Point with the
	 * parameter Double3 with the parameter values.
	 *
	 * @param xyz , Initialize the field with the parameter.
	 */
	public Point(Double3 xyz)
	{
		this.xyz = xyz;
	}

	/**
	 * three double parameter constructor that builds a new Point with the
	 * three doubles that represent the three values of the point.
	 *
	 * @param d1 represent the X value in the vector
	 * @param d2 represent the Y value in the vector
	 * @param d3 represent the Z value in the vector
	 */
	public Point(double d1, double d2, double d3)
	{
		xyz = new Double3(d1, d2, d3);
	}

	/**
	 * subtracts the current point with the function's Point parameter
	 * @param p the function's Point parameter.
	 * @return a new Vector with the new values after the subtraction.
	 */
	public Vector subtract(Point p)
	{
		return new Vector(xyz.subtract(p.xyz));
	}

	/**
	 * adds the current Point with a different Point from the Vector v parameter.
	 * @param v the function's Vector parameter
	 * @return a new Point with the new values after the addition
	 */
	public Point add(Vector v)
	{
		return new Point(xyz.add(v.xyz));

	}

	/**
	 *
	 * @param p the function's Point parameter
	 * @return the distance squared between the current point and the parameter point.
	 */
	public double distanceSquared(Point p)
	{
		return (((this.xyz.d1-p.xyz.d1)*(this.xyz.d1-p.xyz.d1))+((this.xyz.d2-p.xyz.d2)*(this.xyz.d2-p.xyz.d2))+((this.xyz.d3-p.xyz.d3)*(this.xyz.d3-p.xyz.d3)));
	}


	/**
	 * @param p the function's Point parameter.
	 * @return the distance between the current point and the parameter point using the distanceSquared funciton.
	 */
	public double distance(Point p)
	{
		return Math.sqrt(distanceSquared(p));
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		return (obj instanceof Point other && this.xyz.equals(other.xyz));
	}

	@Override
	public String toString()
	{
		return xyz.toString();
	}
}
