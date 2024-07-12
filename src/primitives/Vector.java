package primitives;

/**
 * A Vector class that represent a vector in space.
 */
public class Vector extends Point
{
    public final static Vector Y=new Vector(0,1,0);
    /**
     *A double parameter Constructor that builds a new vector with three double values
     *that represent the three valus of the Vector.
     *
     * @param d1 represent the X value in the vector
     * @param d2 represent the Y value in the vector
     * @param d3 represent the Z value in the vector
     * @throws IllegalArgumentException in case the new vector is equals to the ZERO vector (0,0,0)
     */
    public Vector(double d1,double d2,double d3) throws IllegalArgumentException{
        super(d1,d2,d3);
        if(new Double3(d1,d2,d3).equals(Double3.ZERO))
            throw new IllegalArgumentException("vector is zero");
    }

    /**
     * A Double3 parameter constructor that builds a new Vector with a Double3 parameter
     * @param dob3 the Double3 parameter
     * @throws IllegalArgumentException in case the new vector is equals to the ZERO vector (0,0,0)
     */
    public Vector( Double3 dob3) throws IllegalArgumentException{
        super(dob3);
        if(dob3.equals(Double3.ZERO))
            throw new IllegalArgumentException ("vector is zero");

    }

    /**
     * @return the length of the vector squared
     */
    public double lengthSquared(){
        return ((this.xyz.d1*this.xyz.d1)+(this.xyz.d2*this.xyz.d2)+(this.xyz.d3*this.xyz.d3));
    }

    /**
     *
     * @return the length of the vector
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /**
     *the function do a vector addition
     * @param v argument type vector
     * @return new vector with the new values after the addition.
     */
    public Vector add(Vector v){
            return new Vector(this.xyz.d1+v.xyz.d1,this.xyz.d2+v.xyz.d2,this.xyz.d3+v.xyz.d3);
    }

    /**
     * multiply each value of the vector with the function parameter.
     *
     * @param d the double parameter of the function
     * @return a new vector with the new values of the multiplication
     */
    public Vector scale(double d){
        return new Vector(this.xyz.d1*d,this.xyz.d2*d,this.xyz.d3*d);
    }

    /**
     * dot-product between the parameter vector and the current vector
     * @param v parameter vector of the function
     * @return the dot-product between the two vectors.
     */
     public double dotProduct(Vector v){
        return this.xyz.d1*v.xyz.d1+this.xyz.d2*v.xyz.d2+this.xyz.d3*v.xyz.d3;
     }

    /**
     * cross-product between the parameter vector and the current vector.
     *
     * @param v the parameter vector of the function
     * @return new vector with the new values after the cross-product action
     */
     public Vector crossProduct(Vector v){
        return new Vector(this.xyz.d2*v.xyz.d3-this.xyz.d3*v.xyz.d2,
                this.xyz.d3*v.xyz.d1-this.xyz.d1*v.xyz.d3,
                this.xyz.d1*v.xyz.d2-this.xyz.d2*v.xyz.d1);
     }

    /**
     *
     * @return a new normalized vector from the current vector
     */
     public  Vector normalize(){
        double len =length();
        return new Vector(this.xyz.d1/len,this.xyz.d2/len,this.xyz.d3/len);
     }
    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;

        return (obj instanceof Vector && super.equals(obj));
    }
    @Override

    public String toString(){
        return super.toString();
    }
}
