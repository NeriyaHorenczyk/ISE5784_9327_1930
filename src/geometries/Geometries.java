package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class that represents a collection of geometries.
 */
public class Geometries extends Intersectable
{
	private final List<Intersectable> interGeometries = new LinkedList<>();

	/**
	 * Default constructor that initializes the list of geometries.
	 */
	public Geometries()
	{
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
		interGeometries.addAll(Arrays.asList(geometries));
	}



	/**
	 * Find all the intersections of the given ray with the geometries.
	 * @param ray the given ray
	 * @return list of all the intersections points of the ray with the geometries.
	 */
	@Override
	public List<Point> findIntersections(Ray ray)
	{
		List<Point> intersections = null;
		for (Intersectable geometry : interGeometries)
		{
			List<Point> geometryIntersections = geometry.findIntersections(ray);
			if (geometryIntersections != null)
			{
				if (intersections == null)
				{
					intersections = new LinkedList<>();
				}
				intersections.addAll(geometryIntersections);
			}
		}
		return intersections;
	}

	@Override
	protected List<GeoPoint> findGeoIntesectionsHelper(Ray ray)
	{
		List<GeoPoint> intersections = null;
		for (Intersectable geometry : interGeometries)
		{
			List<GeoPoint> geometryIntersections = geometry.findGeoIntersections(ray);
			if (geometryIntersections != null)
			{
				if (intersections == null)
				{
					intersections = new ArrayList<>();
				}
				intersections.addAll(geometryIntersections);
			}
		}
		return intersections;
	}
}
