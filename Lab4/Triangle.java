import java.awt.*;

public class Triangle implements Shape{

    private Point a;
    private Point b;
    private Point c;
    private Color color;

    public Triangle(Point a, Point b, Point c, Color color){
        this.a = a;
        this.b = b;
        this.c = c;
        this.color = color;
    }

    // Triangle methods
    public Point getVertexA(){return this.a;}
    public Point getVertexB(){return this.b;}
    public Point getVertexC(){return this.c;}
    public boolean equals(Object o){
        if (o == null)
            return false;
        if (o.getClass() != this.getClass())
            return false;
        Triangle other = (Triangle)o;
        return this.a.equals(other.a) && this.b.equals(other.b) && this.c.equals(other.c) &&
                this.color.equals(other.color);
    }

    //Interface methods
    public Color getColor(){return this.color;}
    public void setColor(Color c){this.color = c;}
    public double getArea(){
        double area;
        int num1 = (a.x*b.y) + (b.x*c.y) + (c.x*a.y);
        int num2 = (a.y*b.x) + (b.y*c.x) + (c.y*a.x);
        area = Math.abs(.5*(num1 - num2));
        return area;
    }
    public double getPerimeter(){
        double dist1 = Math.sqrt(Math.pow((double)(a.x - b.x), 2) + Math.pow((double)(a.y - b.y), 2));
        double dist2 = Math.sqrt(Math.pow((double)(b.x - c.x), 2) + Math.pow((double)(b.y - c.y), 2));
        double dist3 = Math.sqrt(Math.pow((double)(a.x - c.x), 2) + Math.pow((double)(a.y - c.y), 2));
        return (dist1 + dist2 + dist3);
    }
    public void translate(Point p){
        int new_a_x = a.x + p.x;
        int new_a_y = a.y + p.y;
        this.a = new Point(new_a_x, new_a_y);
        int new_b_x = b.x + p.x;
        int new_b_y = b.y + p.y;
        this.b = new Point(new_b_x, new_b_y);
        int new_c_x = c.x + p.x;
        int new_c_y = c.y + p.y;
        this.c = new Point(new_c_x, new_c_y);
    }

}
