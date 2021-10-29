import java.awt.*;

public class Rectangle implements Shape{

    private double width;
    private double height;
    private Point topLeftCorner;
    private Color color;

    public Rectangle(double width, double height, Point topLeftCorner, Color color){
        this.width = width;
        this.height = height;
        this.topLeftCorner = topLeftCorner;
        this.color = color;
    }

    //Rectangle methods
    public double getWidth(){return this.width;}
    public void setWidth(double width){this.width = width;}
    public double getHeight(){return this.height;}
    public void setHeight(double height){this.height = height;}
    public Point getTopLeft(){return this.topLeftCorner;}
    public boolean equals(Object o){
        if (o == null)
            return false;
        if (o.getClass() != this.getClass())
            return false;
        Rectangle other = (Rectangle)o;
        return this.width == other.width && this.height  == other.height &&
                this.topLeftCorner.equals(other.topLeftCorner) && this.color.equals(other.color);
    }

    //interface methods
    public Color getColor(){return this.color;}
    public void setColor(Color c){this.color = c;}
    public double getArea(){return (this.height*this.width);}
    public double getPerimeter(){return (2*this.width + 2*this.height);}
    public void translate(Point p){
        int new_x = topLeftCorner.x + p.x;
        int new_y = topLeftCorner.y + p.y;
        this.topLeftCorner = new Point(new_x, new_y);
    }

}
