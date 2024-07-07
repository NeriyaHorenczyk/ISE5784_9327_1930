package lighting;

import primitives.*;


//תיעוד
import static primitives.Util.alignZero;

public class SpotLight extends PointLight {

    private final Vector direction;

    private double narrowBeam = 1;


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


    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }
}