package geometries;

import geometries.Plane;
import geometries.Polygon;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Triangle class
 */
class TriangleTests {

	@Test
	void getNormal() {
		// Equivalence Partitions tests ======================================================================
		// EP01 test if normal vector is correct
		Triangle triangle = new Triangle(new Point(0, 0, 0), new Point(0, 5, 0), new Point(5, 0, 0));
		Vector normal = new Vector(0, 0, 1);
		assertFalse(normal.equals(triangle.getNormal(new Point(1, 1, 0))) ||
				normal.equals(triangle.getNormal(new Point(-1, -1, 0))), "bad normal to triangle");
	}

	@Test
	void findIntersections(){
		// Equivalence Partitions tests ======================================================================

		// EP01 ray passes through triangle
		Ray ray = new Ray(new Point(3, 3, 2), new Vector(-1, -1, -4));
		Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(1, 5, 0), new Point(6, 0, 0));
		assertEquals(1, triangle.findIntersections(ray).size());
		assertEquals(new Point(2.5, 2.5, 0), triangle.findIntersections(ray).get(0));

		// EP02 ray misses triangle on one side
		ray = new Ray(new Point(3, 3, 2), new Vector(1, 1, -4));
		assertNull(triangle.findIntersections(ray));

		// EP03 ray misses triangle on two side
		ray = new Ray(new Point(3, 3, 2), new Vector(-5, 5.5, -4));
		assertNull(triangle.findIntersections(ray));

		// Boundary value tests ==============================================================================
		// BV01 ray intersects vertex
		ray = new Ray(new Point(1, 0, 3), new Vector(0, 0, -1));
		assertNull(triangle.findIntersections(ray));

		// BV02 ray intersects edge
		ray = new Ray(new Point(1, 0, 3), new Vector(1, 0, -6));
		assertNull(triangle.findIntersections(ray));

		// BV03 ray intersects edge continuation imaginary line
		ray = new Ray(new Point(0.5, 0, 3), new Vector(0, 0, -1));
		assertNull(triangle.findIntersections(ray));

	}

	@Test
	void testFindIntersectionsWithDistance() {
		Triangle triangle = new Triangle(new Point(-10, 0, 0), new Point(10, 0, 0), new Point(0, 0, 10));

		// Test case 01: Ray intersects the triangle within a large distance
		List<Point> result = triangle.findIntersections(new Ray(new Point(0, -2, 2), new Vector(0, 1, 1)), 500);
		// TC01: Expected result size is 1
		assertEquals(1, result.size(), "Error: Ray should intersect the triangle within the distance (TC01)");

		// Test case 02: Ray does not intersect the triangle within a very small distance
		result = triangle.findIntersections(new Ray(new Point(0, -2, 2), new Vector(0, 1, 1)), 1);
		// TC02: Expected result is null (no intersection within the distance)
		assertNull(result, "Error: Ray should not intersect the triangle within the distance (TC02)");
	}

}