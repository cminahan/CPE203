import processing.core.PImage;
import java.util.Random;
import java.util.List;
import java.util.Optional;

public class SGrass implements ActivityEntity{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    private final int resourceCount;
    private final int actionPeriod;
    private final int animationPeriod;
    private static final Random rand = new Random();

    public SGrass(String id, Point position, List<PImage> images, int actionPeriod){
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

    //function methods
    public void nextImage() {this.imageIndex = (this.imageIndex + 1) % this.images.size();}

    public static SGrass createSgrass(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new SGrass(id, position, images, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.position);

        if (openPt.isPresent())
        {
            ActivityEntity fish = Fish.createFish(Functions.FISH_ID_PREFIX + this.id,
                    openPt.get(), Functions.FISH_CORRUPT_MIN +
                            rand.nextInt(Functions.FISH_CORRUPT_MAX - Functions.FISH_CORRUPT_MIN),
                    imageStore.getImageList(Functions.FISH_KEY));
            world.addEntity(fish);
            (fish).scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                ActivityAction.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                ActivityAction.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }


}
