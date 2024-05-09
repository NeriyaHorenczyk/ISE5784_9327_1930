package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry
{
	private final Point q;
	private final Vector normal;

	public Plane(Point p1, Point p2, Point p3)
	{
		this.q = p1;
		normal = null;
	}

	public Plane(Point p, Vector v)
	{
		this.q = p;
		this.normal = v.normalize();
	}


	@Override
	public Vector getNormal(Point p)
	{
		return p.subtract(this.q).crossProduct(this.normal);
	}

	public Vector getNormal()
	{
		return this.normal;
	}
}
