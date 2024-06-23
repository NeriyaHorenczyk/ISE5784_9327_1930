package primitives;

import java.util.List;

/**
 * Ray class that represent a ray in space using a Point and a Vector.
 */
public class  Ray
{
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
		if(points == null)
			return null;

		Point closest = null;
		double minDistance = Double.POSITIVE_INFINITY;

		for(Point p : points)
		{
			double distance = head.distance(p);
			if(distance < minDistance)
			{
				minDistance = distance;
				closest = p;
			}
		}

		return closest;
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
