package scene;

import geometries.Geometries;
import geometries.Geometry;
import lighting.AmbientLight;
import primitives.Color;

/**
 * Scene class represents a scene in the 3D model.
 */
public class Scene
{
	public String name;
	public Color background = new Color(Color.BLACK.getColor());
	public AmbientLight ambientLight = AmbientLight.NONE;
	public Geometries geometries = new Geometries();

	/**
	 * Default constructor that initializes the scene name.
	 * @param name the given name
	 */
	public Scene(String name)
	{
		this.name = name;
	}

	/**
	 * Set the background color of the scene.
	 * @param background the given background color
	 * @return the scene
	 */
	public Scene setBackground(Color background)
	{
		this.background = background;
		return this;
	}

	/**
	 * Set the ambient light of the scene.
	 * @param ambientLight the given ambient light
	 * @return the scene
	 */
	public Scene setAmbientLight(AmbientLight ambientLight)
	{
		this.ambientLight = ambientLight;
		return this;
	}

	/**
	 * Set the geometries of the scene.
	 * @param geometry the given geometries
	 * @return the scene
	 */
	public Scene setGeometry(Geometries geometry)
	{
		this.geometries = geometry;
		return this;
	}


}
