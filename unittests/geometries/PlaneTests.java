package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTests
{
	Point p1=new Point(1,2,3);
	Point p2=new Point(2,1,3);
	Point p3=new Point(1,1,3);
	Plane pel=new Plane(p1,p2,p3);
	Point p100 = new Point(1,0,0);
	Point p010 = new Point(0,1,0);
	Point p001 = new Point(0,0,1);
	Plane testPlane = new Plane(p100,p010,p001);
	@Test
	void testGetNormal()
	{
		Vector res=pel.getNormal();
		// ============ Equivalence Partitions Tests ==============
		// TC01: test if the lenght vec
		assertEquals(1,res.length(),0.000001,
				"the plane normal ist 1");

		//cahking if the res it teh expected normal (1)
		assertEquals(new Vector(0,0,-1),res,
				"the planes normal is not correct");
	}


	@Test
	void testFindIntersections() {

		// Equivalence Partitions tests ======================================================================
		// EP01 test if neither orthogonal nor parallel points intersects with plane
		Plane plane = new Plane(new Point(1, 0, 0), new Vector(0, 1, 0));
		Ray ray = new Ray(new Point(0, -2, 0), new Vector(-3, 6, 0));
		assertEquals(1, plane.findIntersections(ray).size(), "ray intersects plane");
		assertEquals(new Point(-1, 0, 0), plane.findIntersections(ray).get(0), "ray intersects plane");

		// EP02 test if ray after plane and goes in opposite direction
		ray = new Ray(new Point(0, -2, 0), new Vector(3, -4, 0));
		assertNull(plane.findIntersections(ray), "ray does not intersect plane");


		// Boundary Value Analysis tests ======================================================================
		// BV01  ray is parallel to plane (0 points)
		ray = new Ray(new Point(0, -2, 0), new Vector(0, 0, 1));
		assertNull(plane.findIntersections(ray), "ray is parallel to plane");

		// BV02  ray is parallel to plane and is included in plane (0 points)
		ray = new Ray(new Point(2, 0, 0), new Vector(1, 0, 0));
		assertNull(plane.findIntersections(ray), "ray is parallel to plane and is included in plane");

		// BV03  ray is orthogonal to plane and starts before plane (1 point)
		ray = new Ray(new Point(0, -2, 0), new Vector(0, 1, 0));
		assertEquals(1, plane.findIntersections(ray).size(), "ray is orthogonal to plane and starts before plane");
		assertEquals(new Point(0, 0, 0), plane.findIntersections(ray).get(0), "ray is orthogonal to plane and starts before plane");

		// BV04  ray is orthogonal to plane and starts in plane (0 points)
		ray = new Ray(new Point(2, 0, 0), new Vector(0, 1, 0));
		assertNull(plane.findIntersections(ray), "ray is orthogonal to plane and starts in plane");

		// BV05  ray is orthogonal to plane and starts after plane (0 points)
		ray = new Ray(new Point(-1, 1, 0), new Vector(0, 1, 0));
		assertNull(plane.findIntersections(ray), "ray is orthogonal to plane and starts after plane");

		// BV06  ray is neither orthogonal nor parallel to plane and starts in plane (0 points)
		ray = new Ray(new Point(2, 0, 0), new Vector(1, 1, 0));
		assertNull(plane.findIntersections(ray), "ray is neither orthogonal nor parallel to plane and starts in plane");

		// BV07  ray is neither orthogonal nor parallel to plane and starts in plane and starts at reference point (0 points)
		ray = new Ray(new Point(1, 0, 0), new Vector(1, 1, 0));
		assertNull(plane.findIntersections(ray), "ray is neither orthogonal nor parallel to plane and starts in plane and starts at reference point");

	}

	@Test
	void testFindIntersectionsWhitDistance() {
		Ray ray = new Ray(new Point(1, 1, 5), new Vector(-1, 1, -3));
		Plane plane = new Plane(new Point(1, 1, 1), new Point(1, 0, 0), new Point(0, 1, 0));

		// Test case 01: Ray intersects the plane within the given distance
		List<Point> result = plane.findIntersections(ray, 500);
		// TC01: Expected result size is 1
		assertEquals(1, result.size(), "Error: Ray should intersect the plane within the distance (TC01)");

		// Test case 02: Ray does not intersect the plane within the given distance
		result = plane.findIntersections(ray, 1);
		// TC02: Expected result is null (no intersection within the distance)
		assertNull(result, "Error: Ray should not intersect the plane within the distance (TC02)");
	}


}