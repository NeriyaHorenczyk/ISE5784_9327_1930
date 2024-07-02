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
		Geometries geometries = new Geometries(new Sphere(1,new Point(1, 1, 0)),
				new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
				new Triangle(new Point(0, 1, 1), new Point(2, 1,1), new Point(1, 1, 0)),
				new Polygon(new Point(0, 2, 1), new Point(1, 2, 0), new Point(2, 2, 0), new Point(1, 2, 4)));

		assertEquals(3, geometries.findIntersections(new Ray(new Point(0, 2, 0), new Vector(2, -2, 0.5))).size(), "Ray intersects with a Sphere and plane but not with a Triangle");

		//====================== Boundary Values test ==================================================================
		// TC02: no geometry is intersected (0 points)
		geometries.add(new Sphere(3d, new Point(1, 2, 3)));
		assertNull(geometries.findIntersections(new Ray(new Point(6, 7, 8), new Vector(2, 0, -2))),
				"ERROR: no geometry is intersected - not working as expected");



		//BV02 - Ray intersects with only one geometry(plane)
		assertEquals(1, geometries.findIntersections(new Ray(new Point(6, 7, 8), new Vector(0, -1, -2))).size(),
				"ERROR: one geometry is intersected - not working as expected");

		//BV03 - Ray intersects with all geometries(4 points)
		assertEquals(6, geometries.findIntersections(new Ray(new Point(1, 4, 8), new Vector(0, -3, -7.8))).size(),
				"ERROR: all geometries are intersected - not working as expected");

		//BV04 - geometries is empty
		geometries = new Geometries();
		assertNull(geometries.findIntersections(new Ray(new Point(0.5, 4, 0.5), new Vector(0, -1, 0))), "geometries is empty");

	}
}