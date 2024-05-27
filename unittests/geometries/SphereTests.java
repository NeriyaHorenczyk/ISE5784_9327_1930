package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}