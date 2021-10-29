import java.awt.Color;
import java.awt.Point;
import java.awt.*;


public interface Shape {

    //methods
    Color getColor();
    void setColor(Color c);
    double getArea();
    double getPerimeter();
    void translate(Point p);
}
