package lighting;
import primitives.*;


//תיעוד
public class PointLight extends Light implements LightSource {
    protected Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;


    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }


    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }


    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }


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
}