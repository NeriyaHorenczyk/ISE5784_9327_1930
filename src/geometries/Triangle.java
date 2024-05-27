package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Triangle class that inherit from Polygon class.
 * represent a new Triangle in space.
 */
public class Triangle extends Polygon
{
	public Triangle(Point p1, Point p2, Point p3)
	{
		super(p1,p2,p3);
	}

	@Override
	public Vector getNormal(Point p)
	{

		return super.getNormal(p);
	}
}
