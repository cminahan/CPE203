import java.util.*;

public class Polygon {

    private ArrayList<Point> points;

    public Polygon(List<Point> points)
    {
        this.points = (ArrayList<Point>) points;
    }

    public List<Point> getPoints() { return points; }
}
