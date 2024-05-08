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
		return new Vector(p.xyz.d1-this.xyz.d1,p.xyz.d2-this.xyz.d2,p.xyz.d3-this.xyz.d3);
	}

	public Point add(Vector v)
	{
		return new Point(this.xyz.d1+v.xyz.d1,this.xyz.d2+v.xyz.d2,this.xyz.d3+v.xyz.d3);
	}

	public double distanceSquared(Point p)
	{
		return (((this.xyz.d1-p.xyz.d1)*(this.xyz.d1-p.xyz.d1))+((this.xyz.d2-p.xyz.d2)*(this.xyz.d2-p.xyz.d2))+((this.xyz.d3-p.xyz.d3)*(this.xyz.d3-p.xyz.d3)));
	}


	/**
	 * @param p
	 * @return the destans betwin the point and the val
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
