import java.util.List;
import java.util.Random;
import processing.core.PImage;

public class Fish implements ActivityEntity{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    private final int resourceCount;
    private final int actionPeriod;
    private final int animationPeriod;
    private static final Random rand= new Random();

    public Fish(String id, Point position, List<PImage> images, int actionPeriod){
        this.id = id;
        this.position = position;
        this.imageIndex =0;
        this.images = images;
        this.resourceCount = 0;
        this.resourceLimit = 0;
        this.actionPeriod = actionPeriod;
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
    public void nextImage(){this.imageIndex = (this.imageIndex + 1) % this.images.size();}

    //function methods
    public static Fish createFish(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Fish(id, position, images, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.position;  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        ActivityEntity crab = Crab.createCrab(this.id + Functions.CRAB_ID_SUFFIX,
                pos, this.actionPeriod / Functions.CRAB_PERIOD_SCALE,
                Functions.CRAB_ANIMATION_MIN +
                        rand.nextInt(Functions.CRAB_ANIMATION_MAX - Functions.CRAB_ANIMATION_MIN),
                imageStore.getImageList(Functions.CRAB_KEY));

        world.addEntity(crab);
        (crab).scheduleActions(scheduler, world, imageStore);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                ActivityAction.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }


}
