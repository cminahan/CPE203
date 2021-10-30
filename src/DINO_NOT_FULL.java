import processing.core.PImage;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.LinkedList;

public class DINO_NOT_FULL extends Dino {
    public DINO_NOT_FULL(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }

    @Override
    protected void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        ACTIVITY activity = new ACTIVITY(this, world, imageStore);
        Optional<Entity> notFullTarget = world.findNearest(getPosition(), MAIN_CHAR.class);

        if (!notFullTarget.isPresent() ||
                !moveToNotFull(world, notFullTarget.get(), scheduler)){
                //!transformNotFull(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, activity, getActionPeriod());
        }
    }

    private boolean moveToNotFull(WorldModel world, Entity target, EventScheduler scheduler) {
        if (adjacent(getPosition(), target.getPosition())) {
            int temp = getResourceCount() + 1;
            setResourceCount(temp);
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        } else {
            List<Point> finalPath;

            //System.out.println(nextPositionHelper(world, this.getPosition(), searched, target.getPosition(), path));
            /*if (nextPositionHelper(world, target.getPosition(), searched, path, finalPath)) {
                System.out.println("check");
                return (moveToHelper(finalPath.get(0), world, scheduler));
            }*/
            //nextPositionHelper(world, target.getPosition(), searched, path, finalPath);
            //else
            finalPath = nextPositionHelper(world, target.getPosition());
            return (moveToHelper(finalPath.get(1),world, scheduler));
        }
    }

    /*private boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (getResourceCount() >= getResourceLimit())
        {
            OCTO_FULL octo = new OCTO_FULL(getId(), getResourceLimit(),
                    getPosition(), getActionPeriod(), this.getAnimationPeriod(),
                    getImages());
            transformHelper(world, scheduler, imageStore, octo);

            return true;
        }

        return false;
    }*/
}