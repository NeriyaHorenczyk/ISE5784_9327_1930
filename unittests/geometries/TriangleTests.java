package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangleTests
{
	Point p1 =new Point(1, 0, 0);
	Point p2=new Point(0, 1, 0);
	Point p3=new Point(0, 0, 1);
	Triangle tri = new Triangle(p1,p2,p3);
	@Test
	void getNormal()
	{
		// ============ Equivalence Partitions Tests ==============

		Vector result = tri.getNormal(new Point(0, 0, 1));
		// TC01: ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001,
				"Triangle normal is not a unit vector");

		// TC02: test if Triangle is equals 1 ro -1
		assertTrue(result.equals( new Vector(0,0,1))||
						result.equals(new Vector(0,0,-1)),
				" the Triangle not 1 or -1 ");
	}

	@Test
	void testFindIntersections()
	{
		Point p380=new Point(3,8,0);
		Point pN180=new Point(-1,8,0);
		Point pN18N3=new Point(-1,8,-3);
		Triangle testTri=new Triangle(p380,pN180,pN18N3);
		// ============ Equivalence Partitions Tests ==============
		// TC01:
		assertEquals(1,testTri.findIntersections(new Ray(new Point(1,7,0),new Vector(0,1,0))),
				"the point in not in the triangel");
		// TC02:
		assertNull(testTri.findIntersections(new Ray(new Point(-2,7,-1),new Vector(2,1,6))),
				"the is more than zero Intersections ");

		// TC03:
		assertNull(testTri.findIntersections(new Ray(new Point(6,7,-6),new Vector(2,1,6))),
				"the is more than zero Intersections ");
		// ============ Boundary Values Tests ==============
		// TC01:
		assertNull(testTri.findIntersections(new Ray(new Point(0,7,-6.5),new Vector(2,1,6))),
				"the is more than zero Intersections ");
		// TC02:
		assertNull(testTri.findIntersections(new Ray(new Point(1,7,-6),new Vector(2,1,6))),
				"the is more than zero Intersections ");
		// TC03:
		assertNull(testTri.findIntersections(new Ray(new Point(5,7,-5),new Vector(2,1,6))),
				"the is more than zero Intersections ");
	}




}