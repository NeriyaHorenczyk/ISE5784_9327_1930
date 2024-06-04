package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

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

	public List<Point> findIntersections(Ray ray)
	{
		Vector v1= vertices.get(0).subtract(ray.getHead());
		Vector v2= vertices.get(1).subtract(ray.getHead());
		Vector v3= vertices.get(2).subtract(ray.getHead());
		Vector n1= v1.crossProduct(v2).normalize();
		Vector n2= v2.crossProduct(v3).normalize();
		Vector n3=v3.crossProduct(v1).normalize();
		Vector direction=ray.getDirection();
			if((alignZero(direction.dotProduct(n1))>0 )&&(alignZero(direction.dotProduct(n2))>0)&&(alignZero(direction.dotProduct(n3))>0)||
					(alignZero(direction.dotProduct(n1))<0 )&&(alignZero(direction.dotProduct(n2))<0)&&(alignZero(direction.dotProduct(n3))<0))
				return plane.findIntersections(ray);
		return null ;
	}

}
