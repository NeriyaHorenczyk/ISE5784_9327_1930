package primitives;

/**
 * represent a vector to al primitivs
 */
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

    /**
     *
     * @return the squared length of the vector
     */
    public double lengthSquared(){
        return ((this.xyz.d1*this.xyz.d1)+(this.xyz.d2*this.xyz.d2)+(this.xyz.d3*this.xyz.d3));
    }

    /**
     *
     * @return the lenght of the vector
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /**
     *the function do a vector connection
     * @param v argoment type vector
     * @return new vector how vector connection whit tje argoment
     */
    public Vector add(Vector v){
            return new Vector(this.xyz.d1+v.xyz.d1,this.xyz.d2+v.xyz.d2,this.xyz.d3+v.xyz.d3);
    }

    /**
     * the function get a numnber and Vector multiplication whit its
     * @param d a argoment fo double type
     * @return a new vector how Vector multiplication in number
     */
    public Vector scale(double d){
        return new Vector(this.xyz.d1*d,this.xyz.d2*d,this.xyz.d3*d);
    }

    /**
     * the function to a  dot-product betwin are vctoe and ander vector
     * @param v a argoment fo vector type
     * @return the dot-product
     */
     public double dotProduct(Vector v){
        return this.xyz.d1*v.xyz.d1+this.xyz.d2*v.xyz.d2+this.xyz.d3*v.xyz.d3;
     }

    /**
     * the function get as argoment avector and do a Vector multiplication are vetor and the
     * argoment vector
     * @param v a argoment fo vector type
     * @return the new vector
     */
     public Vector crossProduct(Vector v){
        return new Vector(this.xyz.d2*v.xyz.d3-this.xyz.d3*v.xyz.d2,
                this.xyz.d3*v.xyz.d1-this.xyz.d1*v.xyz.d3,
                this.xyz.d1*v.xyz.d2-this.xyz.d2*v.xyz.d1);
     }

    /**
     *
     * @return a new vector how as normalize to the unit vector
     */
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
