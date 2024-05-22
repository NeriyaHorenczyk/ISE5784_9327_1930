package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PointTests
{

	@Test
	void testsubtract()
	{
		// TC01: subtract of vector (1,1,1)
		assertEquals(new Vector(1,1,1),new Point(2,2,2).subtract(new Point(1,1,1)),
				"subtract a vector to point and get a new point");
	}

	@Test
	void testadd()
	{
		// TC01: lengthSquared of vector (2,2,2)
		assertEquals(new Point(2,2,2),new Point(1,1,1).add(new Vector(1,1,1)),
				" add a vector to point and get a new point");
	}

	@Test
	void testdistanceSquared()
	{

	}

	@Test
	void testdistance()
	{
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