import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MAIN_CHAR extends Dino{

    public MAIN_CHAR(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, images, resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }

    @Override
    protected void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        ACTIVITY activity = new ACTIVITY(this, world, imageStore);
    }


    private boolean moveToNotFull(WorldModel world, Entity target, EventScheduler scheduler) {
        if (adjacent(getPosition(), target.getPosition())) {
            int temp = getResourceCount() + 1;
            setResourceCount(temp);
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        return false;
    }
}
