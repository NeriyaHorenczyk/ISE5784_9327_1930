package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Sphere class that inherit from RadialGeometry class.
 */
public class Sphere extends RadialGeometry
{
	private final Point center;

	/**
	 * parameter constructor that builds a new sphere with the given parameter as radius and center.
	 * @param radius the given radius parameter that initialize the new sphere's radius.
	 * @param center the given center parameter that initialize the new sphere's center.
	 */
	public Sphere(double radius, Point center)
	{
		super(radius);
		this.center = center;
	}

	@Override
	public Vector getNormal(Point p)
	{

		return p.subtract(center).normalize();
	}

	@Override
	public List<Point> findIntersections(Ray ray)
	{
		//Intersections is a distans of the radius
		if(center.equals(ray.getHead()))
			return List.of(ray.getPoint(radius));

		//vector u is the new vector of (p-o)
		// tm=the number from dotProduct fo u*v
		//d=potagoras of the lenght p0 and tm
		//th=potagoras of the lenght from p1 to d
		Vector u= center.subtract(ray.getHead());
		double tm=u.dotProduct(ray.getDirection());
		double d= Math.sqrt(u.lengthSquared() - Math.pow(tm,2));
		double th =Math.sqrt(Math.pow(radius,2)-Math.pow(d,2));
		List <Point> resPoinrs =null;

		//bva if the dericton from the d to radius is zero or moer the radius
		//so derint Intersections
		if(d>=radius|| isZero( d-radius))
			return null;

		//tm-th==t1 ||tm+th==t2
		if(tm+th<0||isZero(tm+th))
			return null;
		if(tm-th<0||isZero(tm-th))
			return List.of(ray.getPoint(tm+th));

		return List.of(ray.getPoint(tm-th),ray.getPoint(tm+th));
	}
}
