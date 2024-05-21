package primitives;

/**
 * Ray class that represent a ray in space using a Point and a Vector.
 */
public class Ray
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
}
