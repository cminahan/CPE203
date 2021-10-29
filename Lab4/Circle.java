import java.awt.*;

public class Circle implements Shape{
    private double radius;
    private Point center;
    private Color color;

    public Circle(double radius, Point center, Color color){
        this.radius = radius;
        this.center = center;
        this.color = color;
    }

    //Circle methods
    public double getRadius(){return this.radius;}
    public void setRadius(double radius){this.radius = radius;}
    public Point getCenter(){return this.center;}
    public boolean equals(Object o){
        if(o == null)
            return false;
        if(o.getClass() != this.getClass())
            return false;
        Circle other = (Circle)o;
        return this.center.equals(other.center) && this.color.equals(other.color) && this.radius == other.radius;
    }

    //interface methods
    public Color getColor(){return this.color;}
    public void setColor(Color c){this.color = c;}
    public double getArea(){return (Math.PI*this.radius*this.radius);}
    public double getPerimeter(){return (Math.PI*2*this.radius);}
    public void translate(Point p){
        int new_x = p.x + center.x;
        int new_y = p.y + center.y;
        this.center = new Point(new_x, new_y);
    }
}
