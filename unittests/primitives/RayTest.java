package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest
{

	@Test
	void testFindClosestPoint()
	{
		List<Point> points = new ArrayList<>();
		// ============ Equivalence Partitions Tests ==============

		// TC01: The closest point is in the middle of the list
		Ray ray1 = new Ray(new Point(5, 5.5, 0), new Vector(1, 0, 0));
		for(int i = 1; i <= 10; i++)
		{
			points.add(new Point(i, i, 0));
		}
		assertEquals(new Point(5,5,0), ray1.findClosestPoint(points));


		// ================= Boundary Values Tests ==================

		// TC02: The list is empty
		List<Point> emptyList = new ArrayList<>();
		assertNull(ray1.findClosestPoint(emptyList), "The list is empty");

		// TC03: The closest point is the first point in the list
		Ray ray2 = new Ray(new Point(1,1.5,0), new Vector(1,0,0));
		assertEquals(new Point(1,1,0), ray2.findClosestPoint(points), "The closest point is the first point in the list");

		// TC04: The closest point is the last point in the list
		Ray ray3 = new Ray(new Point(10,10.5,0), new Vector(1,0,0));
		assertEquals(new Point(10,10,0), ray3.findClosestPoint(points), "The closest point is the last point in the list");
	}
}