import java.awt.*;
import java.util.LinkedList;

public class WorkSpace{

    private LinkedList<Shape> shapeList;

    public WorkSpace(){
        this.shapeList = new LinkedList<>();
    }

    //Workspace methods
    public void add(Shape shape){
        this.shapeList.add(shape);
    }
    public Shape get(int index){return this.shapeList.get(index);}
    public int size(){return this.shapeList.size();}
    public LinkedList<Circle> getCircles(){
        LinkedList<Circle> circleList = new LinkedList<>();
        for(Shape item : this.shapeList){
            if(item instanceof Circle){
                circleList.add((Circle) item);
            }
        }
        return circleList;
    }
    public LinkedList<Rectangle> getRectangles(){
        LinkedList<Rectangle> rectangleList = new LinkedList<>();
        for(Shape item : this.shapeList){
            if(item instanceof Rectangle){
                rectangleList.add((Rectangle) item);
            }
        }
        return rectangleList;
    }
    public LinkedList<Triangle> getTriangles(){
        LinkedList<Triangle> triangleList = new LinkedList<>();
        for(Shape item : this.shapeList){
            if(item instanceof Triangle){
                triangleList.add((Triangle) item);
            }
        }
        return triangleList;
    }
    public LinkedList<Shape> getShapesByColor(Color color){
        LinkedList<Shape> shapeList = new LinkedList<>();
        for(Shape item : this.shapeList){
            if(item.getColor() == color){
                shapeList.add(item);
            }
        }
        return shapeList;
    }
    public double getAreaOfAllShapes(){
        double sum = 0;
        for(Shape item : this.shapeList)
            sum += item.getArea();
        return sum;
    }
    public double getPerimeterOfAllShapes(){
        double sum = 0;
        for(Shape item : this.shapeList)
            sum += item.getPerimeter();
        return sum;
    }
}

