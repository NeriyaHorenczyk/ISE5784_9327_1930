package lighting;

import primitives.Color;

/**
 * Abstract class for all the lights in the scene
 * */
abstract class Light
{

    /**
     * The intensity of the light
     */
    protected final Color intensity;


    /**
     * Constructor
     * @param intensity The intensity of the light
     * */
    protected Light(Color intensity) {
        this.intensity = intensity;

    }

    /**
     * Getter
     * @return The intensity of the light
     * */
    public Color getIntensity() {
        return intensity;
    }



}
