package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * AmbientLight class represents the ambient light in the scene
 */
public class AmbientLight
{
	/** Default value for ambient light */
	public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);
	private final Color intensity;

	/**
	 * Constructor for AmbientLight
	 * @param IA intensity of the light
	 * @param KA coefficient of the light
	 */
	public AmbientLight(Color IA, Double3 KA)
	{
		this.intensity = IA.scale(KA);
	}

	/**
	 * Constructor for AmbientLight
	 * @param IA intensity of the light
	 * @param KA coefficient of the light
	 */
	public AmbientLight(Color IA, double KA)
	{
		this.intensity = IA.scale(KA);
	}

	/**
	 * Getter for the intensity of the light
	 * @return the intensity of the light
	 */
	public Color getIntensity()
	{
		return intensity;
	}


}
