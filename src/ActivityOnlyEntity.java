import processing.core.PImage;

import java.util.List;

public abstract class ActivityOnlyEntity extends ActionEntity{
    public ActivityOnlyEntity(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        ACTIVITY activity = new ACTIVITY(this, world, imageStore);

        scheduler.scheduleEvent(this, activity, getActionPeriod());
    }
}
