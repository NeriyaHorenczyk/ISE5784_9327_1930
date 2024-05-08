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

    public double lengthSquared(){
        return ((this.xyz.d1*this.xyz.d1)+(this.xyz.d2*this.xyz.d2)+(this.xyz.d3*this.xyz.d3));
    }

    public double length(){
        return Math.sqrt(lengthSquared());
    }

    public Vector add(Vector v){
            return new Vector(this.xyz.d1+v.xyz.d1,this.xyz.d2+v.xyz.d2,this.xyz.d3+v.xyz.d3);
    }
    public Vector scale(double d){
        return new Vector(this.xyz.d1*d,this.xyz.d2*d,this.xyz.d3*d);
    }
     public double dotProduct(Vector v){
        return this.xyz.d1*v.xyz.d1+this.xyz.d2*v.xyz.d2+this.xyz.d3*v.xyz.d3;
     }
     public Vector crossProduct(Vector v){
        return new Vector(this.xyz.d2*v.xyz.d3-this.xyz.d3*v.xyz.d2,
                this.xyz.d3*v.xyz.d1-this.xyz.d1*v.xyz.d3,
                this.xyz.d1*v.xyz.d2-this.xyz.d2*v.xyz.d1);
     }
     public  Vector normalize(){
        double len =length();
        return new Vector(this.xyz.d1/len,this.xyz.d2/len,this.xyz.d3/len);
     }
    @Override
    public boolean equals(Object obj){
        return  (( this== obj)||super.equals(obj)) ;

    }
    @Override
    public String toString(){
        return super.toString();
    }



}
