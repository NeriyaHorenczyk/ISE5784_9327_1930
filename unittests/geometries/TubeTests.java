package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


import static org.junit.jupiter.api.Assertions.*;

class TubeTests
{

	@Test
	void getNormal()
	{
		// ============ Equivalence Partitions Tests ==============
		// TC01: test if all the points are the same
		Tube tube = new Tube(1, new Ray(new Point(1, 0, 0), new Vector(0, 1, 0)));
		assertEquals(new Vector(0, 0, 1), tube.getNormal(new Point(1, 1, 3)),
				"Tube normal is not a unit vector");

		// ================= Boundary Values Tests ==================
		// TC02: test when p-p0 is orthogonal to the axis
		assertThrows(IllegalArgumentException.class, () -> tube.getNormal(new Point(1, 1, 0)),
				"Tube normal is orthogonal to the axis");
	}
}