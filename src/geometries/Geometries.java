package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;

/**
 * Geometries class that represents a collection of geometries.
 */
public class Geometries extends Intersectable
{

	private final List<Intersectable> intersectables=new LinkedList<>();;


	/**
	 * Default constructor that initializes the list of geometries.
	 */
	public Geometries()
	{
		//this.intersectables=new LinkedList<>();
	}
	/**
	 * Constructor that initializes the list of geometries.
	 * @param geometries the given geometries
	 */
	public Geometries(Intersectable... geometries)
	{
		add(geometries);
	}

	/**
	 * Add geometries to the list of geometries.
	 * @param geometries the given geometries
	 */
	public void add(Intersectable... geometries)
	{
		Collections.addAll(this.intersectables, geometries);

	}



	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
	{


		List<GeoPoint> result = null;
		for (Intersectable shape : intersectables) {

			List<GeoPoint> shapePoints = shape.findGeoIntersectionsHelper(ray,maxDistance);
			if (shapePoints != null) {
				if (result == null) {
					result = new LinkedList<>();
				}
				result.addAll(shapePoints);
			}
		}
		return result;

	}
}
