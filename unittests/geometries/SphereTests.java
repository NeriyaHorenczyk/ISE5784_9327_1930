package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SphereTests
{

	@Test
	void getNormal()
	{
		Point p1=new Point(0,0,2);
		Point p2=new Point(0,0,1);
		Sphere s1=new Sphere(1,p2);
		Vector spheraNormai=s1.getNormal(p1);

		assertTrue(spheraNormai.equals(new Vector(0,0,1))||
				spheraNormai.equals(new Vector(0,0,-1)),
				" the sphera normal is not equals to the vector ");

		assertEquals(1,spheraNormai.length(),
				" the normal lenght is not 1 ");

	}
}