import processing.core.PImage;

import java.util.List;

public class Quake implements AnimationEntity, ActivityEntity {
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    private final int resourceCount;
    private final int actionPeriod;
    private final int animationPeriod;

    public Quake(Point position, List<PImage> images){
        this.id = Functions.QUAKE_ID;
        this.position = position;
        this.imageIndex =0;
        this.images = images;
        this.resourceCount = 0;
        this.resourceLimit = 0;
        this.actionPeriod = Functions.QUAKE_ACTION_PERIOD;
        this.animationPeriod = Functions.QUAKE_ANIMATION_PERIOD;
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

    public static Quake createQuake(Point position, List<PImage> images)
    {
        return new Quake(position, images);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                ActivityAction.createActivityAction(this, world, imageStore),
                this.actionPeriod);
        scheduler.scheduleEvent(this,
                AnimationAction.createAnimationAction(this, Functions.QUAKE_ANIMATION_REPEAT_COUNT),
                this.animationPeriod);
    }
}
