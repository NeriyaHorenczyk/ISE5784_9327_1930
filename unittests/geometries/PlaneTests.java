package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray does not start on the plane and intersect with the plane
		Ray testRay110 = new Ray(new Point(1,1,1),new Vector(-1,1,0));
		assertEquals(1,testPlane.findIntersections(testRay110).size(),
				"There are more than 2 Points.");

		//TC02: Ray does not start on the plane and does not intersect with the plane
		Ray testRay222 = new Ray(new Point(1,1,1),new Vector(2,2,2));
		assertEquals(0,testPlane.findIntersections(testRay222).size(),
				"The Ray intersect with the Plane.");

		// =============== Boundary Values Tests ==================

		//Group01: Ray parallel to the Plane
		//TC03:

	}
}