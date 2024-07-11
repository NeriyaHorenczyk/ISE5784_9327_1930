package lighting;
import primitives.*;


/**
 * PointLight class represents the point light in the scene
 */

public class PointLight extends Light implements LightSource {
    protected Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;


    /**
     * Constructor
     * @param intensity The intensity of the light
     * @param position The position of the light
     * */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Set the kC parameter
     * @param kC The constant attenuation factor
     * @return the Point Light object
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Set the kL parameter
     * @param kL The linear attenuation factor
     * @return the Point Light object
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Set the kQ parameter
     * @param kQ The quadratic attenuation factor
     * @return the Point Light object
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }


    @Override
    public Color getIntensity(Point p) {
        double distance = position.distance(p);
        return intensity.scale(1 / (kC + kL * distance + kQ * distance * distance));
    }


    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();
    }

    @Override
    public double getDistance(Point point)
    {
        return position.distance(point);
    }
}