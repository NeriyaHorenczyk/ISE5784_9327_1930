package lighting;

import primitives.Color;


//נריה הבמה שלך בכול המחלקה

abstract class Light {


    protected final Color intensity;



    protected Light(Color intensity) {
        this.intensity = intensity;

    }


    public Color getIntensity() {
        return intensity;
    }



}
