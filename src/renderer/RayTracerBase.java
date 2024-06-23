package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * RayTracerBase class represents a base class for ray tracing.
 */

abstract class RayTracerBase
{
	protected Scene scene;

	/**
	 * Default constructor that initializes the scene.
	 * @param scene the given scene
	 */
	public RayTracerBase(Scene scene)
	{
		this.scene = scene;
	}

	/**
	 * Trace a ray and return the color of the pixel.
	 * @param ray the given ray
	 * @return the color of the pixel
	 */
	abstract public Color traceRay(Ray ray);
}
