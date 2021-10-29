public class Bigger {

    public static double whichIsBigger(Circle circle, Rectangle rectangle, Polygon polygon){
        double c = circle.perimeter();
        double r = rectangle.perimeter();
        double p = polygon.perimeter();
        double max1 = Math.max(c,r);
        return (Math.max(max1, p));
    }
}
