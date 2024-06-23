package scene;

import geometries.Geometries;
import geometries.Geometry;
import lighting.AmbientLight;
import primitives.Color;

public class Scene
{
	public String name;
	public Color background = new Color(Color.BLACK.getColor());
	public AmbientLight ambientLight = AmbientLight.NONE;
	public Geometries geometries = new Geometries();

	public Scene(String name)
	{
		this.name = name;
	}

	public Scene setBackground(Color background)
	{
		this.background = background;
		return this;
	}

	public Scene setAmbientLight(AmbientLight ambientLight)
	{
		this.ambientLight = ambientLight;
		return this;
	}

	public Scene setGeometry(Geometries geometry)
	{
		this.geometries = geometry;
		return this;
	}


}
