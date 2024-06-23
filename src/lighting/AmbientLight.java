package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight
{
	public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);
	private final Color intensity;

	public AmbientLight(Color IA, Double3 KA)
	{
		this.intensity = IA.scale(KA);
	}

	public AmbientLight(Color IA, double KA)
	{
		this.intensity = IA.scale(KA);
	}

	public Color getIntensity()
	{
		return intensity;
	}


}
