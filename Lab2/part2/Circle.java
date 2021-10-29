public class Circle {

    private final Point center;
    private final double radius;

    Circle (Point center, double radius){
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() { return center; }

    public double getRadius() { return radius; }

    public double perimeter(){
        return (2*Math.PI*this.getRadius());
    }


}
