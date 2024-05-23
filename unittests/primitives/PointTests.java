package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PointTests
{
	Point p1=new Point(1,1,1);
	Point p2=new Point(2,2,2);
	Vector v1=new Vector(1,1,1);
	@Test
	void testsubtract()
	{
		// TC01: test the subtract of vector (1,1,1)
		assertEquals(v1, p2.subtract(p1),
				"subtract a vector to point and get a new point");
	}

	@Test
	void testadd()
	{
		// TC01:test the lengthSquared of vector (2,2,2)
		assertEquals(p2,p1.add(v1),
				" add a vector to point and get a new point");
	}

	@Test
	void testdistanceSquared()
	{
		// TC01: test the distanceSquared of point (1,1,1) and point(2,2,2)
		assertEquals(3,p1.distanceSquared(p2),0.0000001,
				"the distanceSquared isnt corect");
	}

	@Test
	void testdistance()
	{
		// TC01: test the distance of point (1,1,1) and point(2,2,2)
		assertEquals(Math.sqrt(3),p1.distance(p2),0.0000001,
				"the distanceSquared isnt corect");
	}

	@Test
	void testEquals()
	{
	}

	@Test
	void testToString()
	{
	}
}