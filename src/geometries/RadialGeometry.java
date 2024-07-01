package geometries;


/**
 * RadialGeometry is an abstract class that represent all the radial shapes.
 */
abstract public class RadialGeometry extends Geometry
{
	protected double radius;

	/**
	 * parameter constructor that build a new shape with the given parameter as a radius.
	 * @param radius the given parameter that represent the new shape's radius.
	 */
	public RadialGeometry(double radius)
	{
		if(radius<=0)
			throw new IllegalArgumentException("Invalid radius value. ");
		this.radius = radius;
	}
}
