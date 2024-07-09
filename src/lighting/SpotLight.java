package lighting;

import primitives.*;


//תיעוד
import static primitives.Util.alignZero;

/**
 * SpotLight class represents the spot light in the scene
 */
public class SpotLight extends PointLight {

    private final Vector direction;

    private double narrowBeam = 1;

    /**
     * Constructor
     * @param intensity The intensity of the light
     * @param position The position of the light
     * @param direction The direction of the light
     * */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Vector getL(Point p) {
        // Returns the direction from the point to the light source
        return super.getL(p);
    }

    @Override
    public SpotLight setKc(double kC) {
        return (SpotLight) super.setKc(kC);
    }

    @Override
    public SpotLight setKl(double kL) {
        return (SpotLight) super.setKl(kL);
    }

    @Override
    public SpotLight setKq(double kQ) {
        return (SpotLight) super.setKq(kQ);
    }


    @Override
    public Color getIntensity(Point point) {
        double cos = alignZero(direction.dotProduct(getL(point)));
        return cos <= 0 ? Color.BLACK //
                : super.getIntensity(point).scale(narrowBeam == 1 ? cos //
                : Math.pow(cos, narrowBeam));
    }

    /**
     * Set the narrow beam parameter
     * @param narrowBeam The narrow beam parameter
     * @return the Spot Light object
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }
}