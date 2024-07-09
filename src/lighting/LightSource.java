package lighting;


import primitives.*;

/**
 * Interface for all the light sources in the scene
 * */

public interface LightSource {

    /**
     * Getter
     * @param p The point in the scene
     * @return The intensity of the light at the point
     * */
    public Color getIntensity(Point p);

    /**
     * Getter
     * @param p The point in the scene
     * @return The direction of the light at the point
     * */
    public Vector getL(Point p);

}