
package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Tha class represents a Plane whit tow fileds , one is object of piont and one is objet of normal
 */
public class Plane implements Geometry
{
	private final Point q;
	private final Vector normal;

	/**
	 * the first constractor  get 3 points
	 * @param p1 Initializes the point of the class
	 * @param p2
	 * @param p3
	 */
	public Plane(Point p1, Point p2, Point p3)
	{
		Vector v1 = p1.subtract(p2);
		Vector v2 = p1.subtract(p3);
		Vector normalTemp = v1.crossProduct(v2).normalize();
		this.q = p1;
		this.normal = normalTemp;
	}

	/**
	 * the second constractor get tow argoment point and vector
	 * @param p	Initializes the point of the class
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
		return p.subtract(this.q).crossProduct(this.normal);
	}

	/**
	 * function loading of getNormal
	 * @return the normal
	 */
	public Vector getNormal()
	{
		return this.normal;
	}
}
