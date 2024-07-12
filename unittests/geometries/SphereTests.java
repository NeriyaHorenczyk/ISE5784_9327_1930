package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SphereTests
{
	Point p1=new Point(0,0,2);
	Point p2=new Point(0,0,1);
	Sphere s1=new Sphere(1,p2);
	Vector spheraNormai=s1.getNormal(p1);
	@Test
	void getNormal()
	{

		// ============ Equivalence Partitions Tests ==============
		// TC01: test if the vector sphera is 1 or -1
		assertTrue(spheraNormai.equals(new Vector(0,0,1))||
				spheraNormai.equals(new Vector(0,0,-1)),
				" the sphera normal is not equals to the vector ");
		//  test if the expected normal of sphera is 1
		assertEquals(1,spheraNormai.length(),
				" the normal lenght is not 1 ");

	}

	@Test
	void testFindIntersections()
	{
		Point p400= new Point (4,0,0);
		Point p130= new Point (1,3,0);
		Point p_200=new Point(-2,0,0);
		Point p1_30=new Point (1,-3,0);
		Point p100= new Point (1,0,0);
		Vector v_330=new Vector(-3,3,0);
		Vector v0_60=new Vector(0,-6,0);
		// ============ Equivalence Partitions Tests ==============
		// TC01: zero Inter sections whit the ray
		 Sphere sphereTest=new Sphere(3,p100);
		assertNull(sphereTest.findIntersections(new Ray(new Point(0,-10,15),new Vector(-10,10,32))));

		// TC02:tow Inter sections whit the ray
		Ray RayTest02= new Ray(new Point(7,-3,0),v_330);
			var exp02= List.of(p400,p130);
			var res02=sphereTest.findIntersections(RayTest02).stream().
					sorted(Comparator.comparingDouble(p -> p.distance(RayTest02.getHead()))).toList();
			//test if the list in the sem lenght
		assertEquals(2,sphereTest.findIntersections(RayTest02).size(),
				"the number of is Inter sections more then tow");
		//test if all the point in the list are the sem
		assertEquals(exp02,res02,"wrong points");

		// TC03:one Inter sections whit the ray
		Ray RayTest03= new Ray(new Point(2.5,1.5,0),v_330);
		assertEquals(1,sphereTest.findIntersections(RayTest03).size(),
				"the number of is Inter sections more then one");
		//need more test of the sam point

		// TC04:vector inverse of the sphere
		//-----mybe we need to chang the points-------
		Ray RayTest04= new Ray(new Point(7,-3,0),new Vector(3,-3,0));

		//test if the list in the same lenght
		assertNull(sphereTest.findIntersections(RayTest04));

		// =============== Boundary Values Tests ==================
		//TC01:test if ray start in thr center
		Ray RayTestBva01= new Ray(new Point(1,0,0),new Vector(0,3,0));

		assertEquals(1,sphereTest.findIntersections(RayTestBva01).size(),
				"the number of is Inter sections is more then 0ne");

		//TC02:test if ray start on the sphere and goes through the center
		Ray RayTestBva02= new Ray(p130, v0_60);

		assertEquals(1,sphereTest.findIntersections(RayTestBva02).size(),
				"the number of is Inter sections is more then tow");


		//TC03:test if ray start uot the sphere and Intersections twice and passes through the center
		Ray RayTestBva03= new Ray(new Point(5,0,0),new Vector(-7,0,0));
		var expBva03= List.of(p400,p_200);
		var resBva03=sphereTest.findIntersections(RayTestBva03).stream().
				sorted(Comparator.comparingDouble(p -> p.distance(RayTestBva03.getHead()))).toList();
		assertEquals(2,sphereTest.findIntersections(RayTestBva03).size(),
				"the number of is Intersections is not tow");

		//test if all the points are same
		assertEquals(expBva03,resBva03,"wrong points");

		//TC04:test if ray start on the sphere and Intersections zero
		Ray RayTestBva04= new Ray(p1_30, v0_60);
		assertNull(sphereTest.findIntersections(RayTestBva04),"no Intersections on the sphere ");


		//TC05:test if ray start out the sphere and Intersections zero
		Ray RayTestBva05= new Ray(new Point(1,-6,0),  v0_60);

		assertNull(sphereTest.findIntersections(RayTestBva05),"the is more than zero Intersections ");

		//TC06:test if ray start on the sphere and Intersections one (not passes through the center)
		Ray RayTestBva06= new Ray(new Point(1,-2,0),  v0_60);

		assertEquals(1,sphereTest.findIntersections(RayTestBva06).size(),
				"the number of is Intersections is not one");

		//TC07:test if ray start out the sphere  and dont intersections the sphere (Multiplication of vectors)
		Ray RayTestBva07= new Ray(new Point(0,-4,0),  new Vector(1,0,0));
		assertNull(sphereTest.findIntersections(RayTestBva07),"the isnt Intersections whit the sphere ");


		//TC08:one point Interections whit the sphere
		Ray RayTestBva08= new Ray(new Point(0,-4,0),  new Vector(0,2,2));
		assertNull(sphereTest.findIntersections(RayTestBva08),"the isnt Intersections whit the sphere ");


		//TC09:point uot the sphere and the verctor Multiplication
		Ray RayTestBva09= new Ray(new Point(4.5,0,0),  new Vector(0,1,0));
		assertNull(sphereTest.findIntersections(RayTestBva09),"the isnt Intersections whit the sphere");

		//TC10: the ray are vertical to the center
		Ray RayTestBva10= new Ray(new Point(0,-4.07,0),  new Vector(-2.37,-4.03,0));
		assertNull(sphereTest.findIntersections(RayTestBva10),"the isnt Intersections whit the sphere");

		//TC11: one Intersections but not whit the center
		Ray RayTestBva11= new Ray(new Point(1.28,2.09,2.13),  new Vector(0.62,4.13,-0.04));
		assertEquals(1,sphereTest.findIntersections(RayTestBva11).size(),
				"the number of is Inter sections is not one");


		// TC12:zero Intersections but not and tow whit the ray taile
		Ray RayTestBva12= new Ray(new Point(0.96,3,0),  new Vector(1.6,8.98,0));
		assertNull(sphereTest.findIntersections(RayTestBva12),
				"the isnt Intersections whit the sphere");

	}
	@Test
	void testFindIntersectionsWithDistance() {
		Sphere sphere = new Sphere(1, new Point(0, 0, 0));

		// Test case 01: Ray intersects the sphere at two points within a large distance
		List<Point> result = sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(-1, 0, 0)), 1000);
		assertEquals(2, result.size(), "Error: Ray should intersect the sphere at two points within the distance (TC01)");

		// Test case 02: Ray does not intersect the sphere within a very small distance
		result = sphere.findIntersections(new Ray(new Point(25, 0, 0), new Vector(-3, 0, 0)), 10);
		assertNull(result, "Error: Ray should not intersect the sphere within the distance (TC02)");
	}
}