package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * AmbientLight class represents the ambient light in the scene
 */
public class AmbientLight extends Light
{
	/** Default value for ambient light */
	public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);


	//נריה הבמה שלך
	public AmbientLight(Color IA, Double3 KA)
	{
		super(IA.scale(KA));
	}

	//נריה הבמה שלך

	public AmbientLight(Color IA, double KA)
	{
		super(IA.scale(KA));
	}




}
