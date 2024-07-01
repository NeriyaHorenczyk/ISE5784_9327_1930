
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Tha class represents a Plane whit tow fileds , one is object of piont and one is objet of normal
 */
public class Plane extends Geometry
{
	private final Point q;
	private final Vector normal;

	/**
	 * the first constractor  get 3 points
	 *
	 * @param p1 Initializes the point of the class
	 * @param p2
	 * @param p3
	 */
	public Plane(Point p1, Point p2, Point p3)
	{

		this.q = p1;
		this.normal = p2.subtract(p1).crossProduct(p3.subtract(p1));

	}

	/**
	 * the second constractor get tow argoment point and vector
	 *
	 * @param p Initializes the point of the class
	 * @param v Initializes the vector of the class and do it a normal vector
	 */
	public Plane(Point p, Vector v)
	{
		this.q = p;
		this.normal = v.normalize();
	}


	@Override
	public Vector getNormal(Point p)
	{
		return this.normal.normalize();
	}

	/**
	 * function loading of getNormal
	 *
	 * @return the normal
	 */
	public Vector getNormal()
	{
		return this.normal.normalize();
	}

	@Override
	public List<Point> findIntersections(Ray ray)
	{
		//q -p=0
		if (q.equals(ray.getHead()))
			return null;
		//A case where the ray is parallel to the plane
		if (isZero(normal.dotProduct(ray.getDirection())))
			return null;

		double t = normal.dotProduct(q.subtract(ray.getHead())) / normal.dotProduct(ray.getDirection());
		if (isZero(t) || t <= 0)
			return null;
		return List.of(ray.getPoint(t));

	}
}
