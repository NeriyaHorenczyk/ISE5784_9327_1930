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
		Ray testRay111 = new Ray(new Point(1,1,1),new Vector(-1,1,0));
		assertEquals(1,testPlane.findIntersections(testRay111).size(),
				"There are more than 2 Points.");

		//TC02: Ray does not start on the plane and does not intersect with the plane
		Ray testRay222 = new Ray(new Point(1,1,1),new Vector(2,2,2));
		assertEquals(0,testPlane.findIntersections(testRay222).size(),
				"The Ray intersect with the Plane.");

		// =============== Boundary Values Tests ==================

		//Group01: Ray parallel to the Plane
		// TC03: Ray included in the Plane
		Ray testRay100 = new Ray(new Point(1,0,0),new Vector(1,0,0));
		assertEquals(0,testPlane.findIntersections(testRay100).size(),
				"The Ray intersect with the Plane (parallel Ray included).");

		// TC04: Ray not included in the Plane
		Ray testRay200 = new Ray(new Point(1,0,0),new Vector(0,1,0));
		assertEquals(0,testPlane.findIntersections(testRay200).size(),
				"The Ray intersect with the Plane (parallel Ray not included).");

		//Group02: Ray is orthogonal to the Plane
		// TC05: Ray starts before the Plane
		Ray testRay010 = new Ray(new Point(0,1,0),new Vector(0,0,1));
		assertEquals(1,testPlane.findIntersections(testRay010).size(),
				"The Ray does not intersect with the Plane (orthogonal Ray starts before the Plane).");

		// TC06: Ray starts in the Plane
		Ray testRay000 = new Ray(new Point(0,0,0),new Vector(0,0,1));
		assertEquals(0,testPlane.findIntersections(testRay000).size(),
				"The Ray intersect with the Plane (orthogonal Ray starts in the Plane).");

		// TC07: Ray starts after the Plane
		Ray testRay110 = new Ray(new Point(1,1,0),new Vector(0,0,1));
		assertEquals(0,testPlane.findIntersections(testRay110).size(),
				"The Ray intersect with the Plane (orthogonal Ray starts after the Plane).");

		//Group03: Special cases
		// TC08: Ray is neither orthogonal nor parallel to and begins at the plane
		assertEquals(1,testPlane.findIntersections(testRay111).size(),
				"The Ray does not intersect with the Plane (Ray is neither orthogonal nor parallel to and begins at the plane).");

		// TC09: Ray is neither orthogonal nor parallel to and begins in the plane point parameter
		assertEquals(1,testPlane.findIntersections(new Ray(p100,new Vector(0,0,2))).size(),
				"The Ray intersect with the Plane (Ray is neither orthogonal nor parallel to and begins in the plane point parameter).");
	}
}