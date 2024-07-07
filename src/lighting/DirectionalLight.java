package lighting;

import primitives.*;
//להוסיף תיעוד

public class DirectionalLight extends Light implements LightSource {

    private Vector direction;


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
}