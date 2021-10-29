public class Point{

    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    /*public static void main(String args[]){
        Point obj = new Point();
    }*/

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getRadius(){
        double y = getY();
        y = y*y;
        double x = getX();
        x = x*x;
        double dist = Math.sqrt(x+y);
        return dist;
    }

    public double getAngle(){
        double tan = Math.atan2(y, x);
        return tan;
    }

    public Point rotate90(){
        double yPoint = y*-1;
        Point p = new Point(yPoint, x);
        return p;
    }
}
