package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable interface represents all the geometric shapes that can be intersected by a ray.
 */
public interface Intersectable
{
	/**
	 * findIntersections function that finds all the intersections of the given ray with the shape.
	 * @param ray the given ray parameter
	 * @return list of all the intersections points of the ray with the shape.
	 */
	public List<Point> findIntersections(Ray ray);
}
