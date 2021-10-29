import processing.core.PImage;
import java.util.List;

public class Atlantis implements AnimationEntity, ActivityEntity {
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int actionPeriod;
    private final int animationPeriod;

    public Atlantis(String id, Point position, List<PImage> images){
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = 0;
        this.animationPeriod = 0;
    }

    //getter methods
    public Point getPosition(){return this.position;}
    public void setPosition(Point p){this.position = p;}
    public List<PImage> getImages(){return this.images;}
    public String getId(){return this.id;}
    public int getImageIndex(){return this.imageIndex;}
    public int getAnimationPeriod(){return this.animationPeriod;}
    public int getActionPeriod(){return this.actionPeriod;}
    public void nextImage() {this.imageIndex = (this.imageIndex + 1) % this.images.size();}

    //function methods
    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){}

    public static Atlantis createAtlantis(String id, Point position, List<PImage> images)
    {
        return new Atlantis(id, position, images);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    public static void scheduleActions(Entity entity, EventScheduler scheduler){
        scheduler.scheduleEvent(entity,
                AnimationAction.createAnimationAction(entity, Functions.ATLANTIS_ANIMATION_REPEAT_COUNT),
                entity.getAnimationPeriod());
    }
}
