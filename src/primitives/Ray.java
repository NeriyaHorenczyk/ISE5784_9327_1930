package primitives;

import java.util.Comparator;
import java.util.List;
import geometries.Intersectable.GeoPoint;

/**
 * Ray class that represent a ray in space using a Point and a Vector.
 */
public class  Ray
{

	private static final double DELTA = 0.1;


	private Point head;
	private Vector direction;

	/**
	 * Parameter constructor that builds a new Ray with the function's parameters
	 * @param p the Point parameter that represent the head field
	 * @param v the Vector parameter that represent the direction field.
	 */
	public Ray(Point p, Vector v)
	{
		head = p;
		direction = v.normalize();
	}

	public Ray(Point point, Vector r, Vector n)
	{
		this(point, r);
		double nv = n.dotProduct(r);
		if(!Util.isZero(nv))
			head = head.add(n.scale(nv > 0 ? DELTA : -DELTA));
	}

	public Point getHead()
	{
		return head;
	}

	public Vector getDirection()
	{
		return direction;
	}

	/**
	 * Find the closest point to the head of the ray from a list of points.
	 * @param points the given list of points
	 * @return the closest point to the head of the ray from the list of points.
	 */

	public Point findClosestPoint(List<Point> points)
	{
		return points == null || points.isEmpty() ? null
				: findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}

	public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoint)
	{
		return geoPoint.stream().min(Comparator.comparingDouble(p->p.point.distance(head))).orElse(null);
	}
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		return obj instanceof Ray other
				&& this.head.equals(other.head)
				&& this.direction.equals(other.direction);
	}

	@Override
	public String toString()
	{
		return head.toString() + '\n' + direction.toString();
	}

	public Point getPoint(double d) {
		if(Util.isZero(d))
			return getHead();

		return getHead().add(getDirection().scale(d));
	}
}
