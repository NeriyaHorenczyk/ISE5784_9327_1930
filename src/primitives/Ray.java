package primitives;

public class Ray
{
	private Point head;
	private Vector direction;

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
				&& this.head.equals(((Ray) obj).head)
				&& this.direction.equals(((Ray) obj).direction);
	}

	@Override
	public String toString()
	{
		return head.toString() + '\n' + direction.toString();
	}
}
