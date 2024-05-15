package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * Geometry interface represents all the geometric shapes
 */
public interface Geometry
{
	/**
	 *
	 * @param p the function's Point parameter
	 * @return normal vector on the body from the Point parameter
	 */
	public Vector getNormal(Point p);
}
