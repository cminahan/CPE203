import java.util.*;

public class Polygon {

    private ArrayList<Point> points;

    public Polygon(List<Point> points) {
        this.points = (ArrayList<Point>) points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public double perimeter() {
        double perim = 0.0;
        for (int i = 0; i < this.getPoints().size(); i++) {
            double x;
            double y;
            if (i == this.getPoints().size() - 1) {
                x = Math.pow(this.getPoints().get(i).getX() - this.getPoints().get(0).getX(), 2);
                y = Math.pow(this.getPoints().get(i).getY() - this.getPoints().get(0).getY(), 2);
            } else {
                x = Math.pow(this.getPoints().get(i).getX() - this.getPoints().get(i + 1).getX(), 2);
                y = Math.pow(this.getPoints().get(i).getY() - this.getPoints().get(i + 1).getY(), 2);
            }
            perim += Math.sqrt(x + y);
        }
        return perim;
    }
}
