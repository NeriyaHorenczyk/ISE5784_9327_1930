package lighting;

import primitives.*;

/**
 * DirectionalLight class represents the directional light in the scene
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * The direction of the light
     */
    private Vector direction;

    /**
     * Constructor
     * @param intensity The intensity of the light
     * @param direction The direction of the light
     * */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}