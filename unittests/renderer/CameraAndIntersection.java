package renderer;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CameraAndIntersection
{
	Camera.Builder builder = new Camera.Builder().setVpSize(3, 3)
			.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
			.setVpDistance(1);
	//build new camera from the builder
	Camera c1 = builder.setLocation(new Point(0, 0, 0)).build();
	Camera c2 = builder.setLocation(new Point(0, 0, 0.5)).build();

	@Test
	void testSphere()
	{
		Sphere s1 = new Sphere(1, new Point(0, 0, -3));
		Sphere s2 = new Sphere(2.5, new Point(0, 0, -2.5));
		Sphere s3 = new Sphere(2, new Point(0, 0, -2));
		Sphere s4 = new Sphere(4, new Point(0, 0, -2));
		Sphere s5 = new Sphere(0.5, new Point(0, 0, 1));

		//all the test are 3x3 test
		// of Intersection Sphere 1 tow points
		int countIntersrctionSphera1 = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (s1.findIntersections(c1.constructRay(3, 3, i, j)) != null)
					countIntersrctionSphera1 += s1.findIntersections(c1.constructRay(3, 3, i, j)).size();

		assertEquals(2, countIntersrctionSphera1,
				"wrong number of of findIntersections");

		//test of Intersection Sphere2 and 18 points
		int countIntersrctionSphera2 = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (s2.findIntersections(c2.constructRay(3, 3, i, j)) != null)
					countIntersrctionSphera2 += s2.findIntersections(c2.constructRay(3, 3, i, j)).size();

		assertEquals(18, countIntersrctionSphera2,
				"wrong number of of findIntersections");

		//test of Intersection Sphere3 and 18 points
		int countIntersrctionSphera3 = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (s3.findIntersections(c2.constructRay(3, 3, i, j)) != null)
					countIntersrctionSphera3 += s3.findIntersections(c2.constructRay(3, 3, i, j)).size();

		assertEquals(10, countIntersrctionSphera3,
				"wrong number of of findIntersections");

		//test of Intersection Sphere4 and 9 points
		int countIntersrctionSphera4 = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (s4.findIntersections(c2.constructRay(3, 3, i, j)) != null)
					countIntersrctionSphera4 += s4.findIntersections(c2.constructRay(3, 3, i, j)).size();

		assertEquals(9, countIntersrctionSphera4,
				"wrong number of of findIntersections");
		//test of Intersection Sphere4 and 0 points
		int countIntersrctionSphera5 = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (s5.findIntersections(c2.constructRay(3, 3, i, j)) != null)
					countIntersrctionSphera5 += s5.findIntersections(c2.constructRay(3, 3, i, j)).size();

		assertEquals(0, countIntersrctionSphera5,
				"wrong number of of findIntersections");
	}


	@Test
	void testPlane()
	{
		Plane p1 = new Plane(new Point(-1, -2, -3), new Vector(0, 0, 1));
		Plane p2 = new Plane(new Point(-1, -2, -3), new Vector(1, 1, 11));
		Plane p3 = new Plane(new Point(0, 0, -5), new Vector(0, 10, -1));

		//test of Intersection plane1 and 9 points
		int countIntersrctionPlean1 = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (p1.findIntersections(c2.constructRay(3, 3, i, j)) != null)
					countIntersrctionPlean1 += p1.findIntersections(c2.constructRay(3, 3, i, j)).size();

		assertEquals(9, countIntersrctionPlean1,
				"wrong number of of findIntersections");

		//test of Intersection plane1 and 9 points
		int countIntersrctionPlean2 = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (p2.findIntersections(c2.constructRay(3, 3, i, j)) != null)
					countIntersrctionPlean2 += p2.findIntersections(c2.constructRay(3, 3, i, j)).size();

		assertEquals(9, countIntersrctionPlean2,
				"wrong number of of findIntersections");

		//test of Intersection plane1 and 6 points
		int countIntersrctionPlean3 = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (p3.findIntersections(c2.constructRay(3, 3, i, j)) != null)
					countIntersrctionPlean3 += p3.findIntersections(c2.constructRay(3, 3, i, j)).size();

		assertEquals(6, countIntersrctionPlean3,
				"wrong number of of findIntersections");

	}
	@Test
	void testTriangle()
	{
		Triangle t1 = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
		Triangle t2 = new Triangle(new Point(0, 20, 2), new Point(1, -1, -2), new Point(-1, -1, -2));

		//test of Intersection Triangle1 and 1 points
		int countIntersrctionTriangle1 = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (t1.findIntersections(c2.constructRay(3, 3, i, j)) != null)
					countIntersrctionTriangle1 += t1.findIntersections(c2.constructRay(3, 3, i, j)).size();

		assertEquals(1, countIntersrctionTriangle1,
				"wrong number of of findIntersections");

		//test of Intersection Triangle1 and 2 points
		int countIntersrctionTriangle2 = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (t2.findIntersections(c2.constructRay(3, 3, i, j)) != null)
					countIntersrctionTriangle2 += t2.findIntersections(c2.constructRay(3, 3, i, j)).size();

		assertEquals(2, countIntersrctionTriangle2,
				"wrong number of of findIntersections");
	}
}