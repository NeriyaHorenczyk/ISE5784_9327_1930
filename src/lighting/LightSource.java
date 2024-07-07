package lighting;


import primitives.*;
//פנק בתיעוד

public interface LightSource {


    public Color getIntensity(Point p);


    public Vector getL(Point p);

}