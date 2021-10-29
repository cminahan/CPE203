import java.lang.Math.*;

public class Util {

    public static double perimeter(Circle circle){
        return (2*Math.PI*circle.getRadius());
    }

    public static double perimeter(Rectangle rectangle){
        double width = Math.abs((rectangle.getTopLeft().getX()) - (rectangle.getBottomRight().getX()));
        double height = Math.abs((rectangle.getTopLeft().getY()) - (rectangle.getBottomRight().getY()));
        return (2*width + 2*height);
    }

    public static double perimeter(Polygon polygon){
        double perim = 0.0;
        for(int i = 0; i < polygon.getPoints().size(); i++){
            double x;
            double y;
            if(i == polygon.getPoints().size() - 1){
                x = Math.pow(polygon.getPoints().get(i).getX() - polygon.getPoints().get(0).getX(), 2);
                y = Math.pow(polygon.getPoints().get(i).getY() - polygon.getPoints().get(0).getY(), 2);
            }
            else{
                x = Math.pow(polygon.getPoints().get(i).getX() - polygon.getPoints().get(i+1).getX(), 2);
                y = Math.pow(polygon.getPoints().get(i).getY() - polygon.getPoints().get(i+1).getY(), 2);
            }
            perim += Math.sqrt(x+y);
        }
        return perim;
    }
}

