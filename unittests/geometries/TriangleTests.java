package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

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





}