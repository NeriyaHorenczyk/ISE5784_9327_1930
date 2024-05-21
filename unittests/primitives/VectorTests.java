package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Vector class
 *
 * @author Neriya
 */
class VectorTests
{

	/**
	 * Delta value for accuracy when comparing the numbers of type 'double' in
	 * assertEquals
	 */
	private final double DELTA = 0.000001;


	/**
	 * Test method for {@link primitives.Vector#Vector(double, double, double)}.
	 * Test method for {@link primitives.Vector#Vector(Double3)}.
	 */
	@Test
	void testVector()
	{
		// ============ Equivalence Partitions Tests ==============
		// TC01: Correct Vector with three double values
		assertDoesNotThrow(() -> new Vector(1, 2, 3), "Failed constructing a correct Vector");

		// TC02: Correct Vector with Double3 parameter
		assertDoesNotThrow(() -> new Vector(new Double3(1, 2, 3)), "Failed constructing a correct Vector");

		// =============== Boundary Values Tests ==================

		// TC03: Vector is zero
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "Constructed a zero vector");

		// TC04: Vector is zero
		assertThrows(IllegalArgumentException.class, () -> new Vector(new Double3(0, 0, 0)), "Constructed a zero vector");

	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared()
	{
		// ============ Equivalence Partitions Tests ==============
		// TC01: lengthSquared of vector (1,2,3)
		assertEquals(14, new Vector(1, 2, 3).lengthSquared(), DELTA, "lengthSquared() wrong value");

		// TC02: lengthSquared of vector (-1,-2,-3)
		assertEquals(14, new Vector(-1, -2, -3).lengthSquared(), DELTA, "lengthSquared() wrong value");

		// TC03: lengthSquared of vector (0,0,0) throws exception
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0).lengthSquared(), "lengthSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength()
	{
		// ============ Equivalence Partitions Tests ==============
		// TC01: length of vector (1,2,3)
		assertEquals(3, new Vector(1, 2, 2).length(), DELTA, "length() wrong value");

		// TC02: length of vector (-1,-2,-3)
		assertEquals(3, new Vector(1, 2, 2).length(), DELTA, "length() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAdd()
	{
		// ============ Equivalence Partitions Tests ==============
		// TC01: add vector (1,2,3) to vector (1,2,3)
		assertEquals(new Vector(2, 4, 6), new Vector(1, 2, 3).add(new Vector(1, 2, 3)), "add() wrong value (positive values)");


		// TC02: add vector (-1,-2,-3) to vector (-1,-2,-3)
		assertEquals(new Vector(-2, -4, -6), new Vector(-1, -2, -3).add(new Vector(-1, -2, -3)), "add() wrong value (negative values)");

		// TC03: add vector (0,0,0) to vector (1,2,3) throws exception
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0).add(new Vector(1, 2, 3)), "add() wrong value (zero vector)");

		// =============== Boundary Values Tests ==================

		// TC04: add vector (1,2,3) to vector (-1,-2,-3) throws exception
		assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).add(new Vector(-1, -2, -3)), "add() wrong value (positive and negative values)");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale()
	{
		// ============ Equivalence Partitions Tests ==============
		// TC01: scale vector (1,2,3) by 2
		assertEquals(new Vector(2, 4, 6), new Vector(1, 2, 3).scale(2), "scale() wrong value (positive scalar and vector values)");

		// TC02: scale vector (-1,-2,-3) by 2
		assertEquals(new Vector(-2, -4, -6), new Vector(-1, -2, -3).scale(2), "scale() wrong value (negative vector and positive scalar values)");

		// TC03: scale vector (1,2,3) by -2
		assertEquals(new Vector(-2, -4, -6), new Vector(1, 2, 3).scale(-2), "scale() wrong value (positive vector and negative scalar values)");

		//TC04: scale vector (-1,-2,-3) by -2
		assertEquals(new Vector(2, 4, 6), new Vector(-1, -2, -3).scale(-2), "scale() wrong value (negative vector and scalar values)");

		// =============== Boundary Values Tests ==================

		// TC05: scale vector (0,0,0) by 2 throws exception
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0).scale(2), "scale() wrong value (zero vector)");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct()
	{
		// ============ Equivalence Partitions Tests ==============

		// TC01: dotProduct of vector (1,2,3) and vector (1,2,3)
		assertEquals(14, new Vector(1, 2, 3).dotProduct(new Vector(1, 2, 3)), DELTA, "dotProduct() wrong value (positive values)");

		// TC02: dotProduct of vector (-1,-2,-3) and vector (-1,-2,-3)
		assertEquals(14, new Vector(-1, -2, -3).dotProduct(new Vector(-1, -2, -3)), DELTA, "dotProduct() wrong value (negative values)");

		//TC03: dotProduct of vector (1,2,3) and vector (-1,-2,-3)
		assertEquals(-14, new Vector(1, 2, 3).dotProduct(new Vector(-1, -2, -3)), DELTA, "dotProduct() wrong value (positive and negative values)");

		// =============== Boundary Values Tests ==================

		// TC04: dotProduct of vector (0,0,0) and vector (1,2,3) throws exception
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0).dotProduct(new Vector(1, 2, 3)), "dotProduct() wrong value (zero vector)");

		// TC05: dotProduct of Vectors that perpendicular to each other
		assertEquals(0, new Vector(1, 0, 0).dotProduct(new Vector(0, 0, 1)), DELTA, "dotProduct() wrong value (perpendicular vectors)");

		//TC06: dotProduct of Vectors that one of them is unit vector
		assertEquals(1, new Vector(1, 2, 3).dotProduct(new Vector(1, 0, 0)), DELTA, "dotProduct() wrong value (unit vector)");


	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct()
	{
		// ============ Equivalence Partitions Tests ==============
		// TC01: crossProduct of vector (1,2,3) and vector (4,5,6)
		assertEquals(new Vector(-3, 6, -3), new Vector(1, 2, 3).crossProduct(new Vector(4, 5, 6)), "crossProduct() wrong value");

		//=============== Boundary Values Tests ==================

		// TC02: crossProduct of vectors that are parallel to each other
		assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).crossProduct(new Vector(2, 4, 6)), "crossProduct() wrong value (parallel vectors)");

		// TC03: crossProduct of vectors that are opposite to each other
		assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).crossProduct(new Vector(-1, -2, -3)), "crossProduct() wrong value (opposite vectors)");

		// TC04: crossProduct of vectors that are equal to each other throws exception
		assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).crossProduct(new Vector(1, 2, 3)), "crossProduct() wrong value (equal vectors)");

	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize()
	{
		// ============ Equivalence Partitions Tests ==============
		// TC01: normalize vector (3,4,0)
		assertEquals(new Vector(0.6, 0.8, 0), new Vector(3, 4, 0).normalize(), "normalize() wrong value (positive values)");

		//=============== Boundary Values Tests ==================

		// TC02: normalize vector (-3,-4,0)
		assertEquals(new Vector(-0.6, -0.8, 0), new Vector(-3, -4, 0).normalize(), "normalize() wrong value (negative values)");

		// TC03: normalize vector (0,0,0)
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0).normalize(), "normalize() wrong value (zero vector)");

		// TC04: normalize vector that is already normalized
		assertEquals(new Vector(0.6, 0.8, 0), new Vector(0.6, 0.8, 0).normalize(), "normalize() wrong value (normalized vector)");

	}
}