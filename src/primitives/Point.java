package primitives;

/**
 * Point class that represent a three values Point in space.
 */
public class Point
{

	protected Double3 xyz;

	/**
	 * This is a static point that represent the (0,0,0) point.
	 */
	public static Point ZERO = new Point(Double3.ZERO);

	/**
	 * This is a parameter constructor
	 *
	 * @param xyz , Initialize the field to the parameter.
	 */
	public Point(Double3 xyz)
	{
		this.xyz = xyz;
	}

	public Point(double d1, double d2, double d3)
	{
		xyz = new Double3(d1, d2, d3);
	}

	public Vector substract(Point p)
	{
		Vector v = new Vector();
		return v;
	}


	/**
	 * @param p
	 * @return the destans betwin the point and the val
	 */
	public double distans(Point p)
	{
		return 0.0;
	}
}
