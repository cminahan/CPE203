import processing.core.PImage;
import java.util.List;

public class Obstacle implements Entity{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    private final int resourceCount;
    private final int actionPeriod;
    private final int animationPeriod;

    public Obstacle(String id, Point position, List<PImage> images){
        this.id = id;
        this.position = position;
        this.imageIndex =0;
        this.images = images;
        this.resourceCount = 0;
        this.resourceLimit = 0;
        this.actionPeriod = 0;
        this.animationPeriod = 0;
    }

    //getter methods
    public String getId(){return this.id;}
    public Point getPosition(){return this.position;}
    public List<PImage> getImages(){return this.images;}
    public int getImageIndex(){return this.imageIndex;}
    public int getResourceLimit(){return this.resourceLimit;}
    public int getResourceCount(){return this.resourceCount;}
    public int getActionPeriod(){return this.actionPeriod;}
    public int getAnimationPeriod(){return this.animationPeriod;}
    public void setPosition(Point p){this.position = p;}

    //function methods
    public void nextImage() {this.imageIndex = (this.imageIndex + 1) % this.images.size();}

    public static Obstacle createObstacle(String id, Point position, List<PImage> images)
    {
        return new Obstacle(id, position, images);
    }

}
