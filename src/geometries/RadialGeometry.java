package geometries;



abstract public class RadialGeometry implements Geometry
{
	protected double radius;

	public RadialGeometry(double radius)
	{
		if(radius<=0)
			throw new IllegalArgumentException("Invalid radius value. ");
		this.radius = radius;
	}
}
