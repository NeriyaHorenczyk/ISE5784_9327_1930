package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTests
{
	Point p1=new Point(1,2,3);
	Point p2=new Point(2,1,3);
	Point p3=new Point(1,1,3);
	Plane pel=new Plane(p1,p2,p3);
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


}