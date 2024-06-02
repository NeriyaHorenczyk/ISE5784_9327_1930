package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests
{


	/**
	 * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntersections()
	{
		// ============ Equivalence Partitions Tests ==============
		// TC01: Some of the geometries intersect

		// =============== Boundary Values Tests ==================
		// TC02: None of the geometries intersect
		// TC03: All geometries intersect
		// TC04: Only one geometry intersect
		// TC05: Empty geometries list

		// TC01: Some of the geometries intersect
		Geometries geometries = new Geometries(new Sphere(1, new Point(0, 0, 0)),
				new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
				new Plane(new Point(0, 0, 1), new Vector(0, 0, 1)));

		assertEquals(2, geometries.findIntersections(new Ray(new Point(0, 0, 2), new Vector(0, 0, -1))).size(), "Some of the geometries intersect");

		// TC02: None of the geometries intersect
		assertNull(geometries.findIntersections(new Ray(new Point(0, 0, 2), new Vector(0, 0, 1))), "None of the geometries intersect");

		// TC03: All geometries intersect
		assertEquals(4, geometries.findIntersections(new Ray(new Point(0, 0, 2), new Vector(0, 0, -1))).size(), "All geometries intersect");

		// TC04: Only one geometry intersect
		assertEquals(1, geometries.findIntersections(new Ray(new Point(0, 0, 2), new Vector(0, 0, -1))).size(), "Only one geometry intersect");

		// TC05: Empty geometries list
		geometries = new Geometries();
		assertNull(geometries.findIntersections(new Ray(new Point(0, 0, 2), new Vector(0, 0, 1))), "Empty geometries list");

	}
}