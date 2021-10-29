public class Point{
    private double x;
    private double y;
    private double z;

    public Point(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //getters and setters
    public void setX(double x){this.x = x;}
    public void setY(double y){this.y = y;}
    public void setZ(double z){this.z = z;}
    public double getX(){return x;}
    public double getY(){return y;}
    public double getZ(){return z;}

    @java.lang.Override
    public java.lang.String toString() {
        return (x + ", " + y + ", " + z);
    }

    public void translate(Point p){
        this.x = x + p.getX();
        this.y = y + p.getY();
    }

    public void scale(double num){
        this.x = x*num;
        this.y= y*num;
        this.z = z*num;
    }

}