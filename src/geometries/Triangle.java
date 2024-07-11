package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Triangle class that inherit from Polygon class.
 * represent a new Triangle in space.
 */
public class Triangle extends Polygon
{
	/**
	 * Constructor that initializes the vertices of the triangle.
	 * @param p1 the first vertex
	 * @param p2 the second vertex
	 * @param p3 the third vertex
	 */
	public Triangle(Point p1, Point p2, Point p3)
	{
		super(p1, p2, p3);
	}


	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance)
	{
		List<Point> intersections = plane.findIntersections(ray,maxDistance);
		List<GeoPoint> result = new ArrayList<GeoPoint>();

		if (intersections == null)
			return null;
		for(Point p : intersections)
		{
			Vector v1 = vertices.get(0).subtract(ray.getHead());
			Vector v2 = vertices.get(1).subtract(ray.getHead());
			Vector v3 = vertices.get(2).subtract(ray.getHead());

			Vector n1 = v1.crossProduct(v2);
			Vector n2 = v2.crossProduct(v3);
			Vector n3 = v3.crossProduct(v1);

			Vector direction = ray.getDirection();
			if ((alignZero(direction.dotProduct(n1)) > 0) && (alignZero(direction.dotProduct(n2)) > 0) &&
					(alignZero(direction.dotProduct(n3)) > 0) ||
					(alignZero(direction.dotProduct(n1)) < 0) && (alignZero(direction.dotProduct(n2)) < 0) &&
							(alignZero(direction.dotProduct(n3)) < 0))
				result.add(new GeoPoint(this, p));
		}
		if (result.isEmpty())
			return null;
		return result;

	}
}
