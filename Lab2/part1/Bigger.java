public class Bigger {

    public static double whichIsBigger(Circle circle, Rectangle rectangle, Polygon polygon){
        double c = Util.perimeter(circle);
        double r = Util.perimeter(rectangle);
        double p = Util.perimeter(polygon);
        double max1 = Math.max(c,r);
        return (Math.max(max1, p));
    }
}
