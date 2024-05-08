package primitives;

public class Vector extends Point
{
    public Vector(double d1,double d2,double d3) throws IllegalArgumentException{
        super(d1,d2,d3);
        Double3 testVectorZero=new Double3(d1,d2,d3);
        if(testVectorZero.equals(Double3.ZERO))
            throw new IllegalArgumentException ("vector is zero");



    }

    public Vector( Double3 dob3) throws IllegalArgumentException{
        super(dob3);
        if(dob3.equals(Double3.ZERO))
            throw new IllegalArgumentException ("vector is zero");

    }
}
