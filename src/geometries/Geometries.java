package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Geometries
{
	private final List<Intersectable> interGeometries = new LinkedList<>();

	public Geometries()
	{
	}

	public Geometries(Intersectable... geometries)
	{
		add(geometries);
	}

	public void add(Intersectable... geometries)
	{

	}

	public List<Point> findIntersections(Ray ray)
	{
		return null;
	}

	//		List<Point> intersections = new ArrayList<>();
//
//	    	for (Intersectable geometry : interGeometries)
//		{
//			List<Point> geometryIntersections = geometry.findIntersections(ray);
//			if (geometryIntersections != null)
//			{
//				intersections.addAll(geometryIntersections);
//			}
//		}
//
//		return intersections.isEmpty() ? null : intersections;
}
