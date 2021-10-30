import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.LinkedList;

public class TRICERA extends Dino {
    public TRICERA(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, images, resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }

    @Override
    protected void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        ACTIVITY activity = new ACTIVITY(this, world, imageStore);
        Optional<Entity> fullTarget = world.findNearest(getPosition(), ATLANTIS.class);

        if (fullTarget.isPresent() &&
                moveToFull(world, fullTarget.get(), scheduler)) {
            if (ActionEntity.class.isInstance(fullTarget.get()))
                //at atlantis trigger animation
                fullTarget.get().scheduleActions(scheduler, world, imageStore);

            //transform to unfull
            //transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, activity, getActionPeriod());
        }
    }

    private boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (adjacent(this.getPosition(), target.getPosition())) {
            return true;
        } else {
            List<Point> finalPath;
            /*if (nextPositionHelper(world, target.getPosition(), searched, path))
                return (moveToHelper(finalPath.get(0), world, scheduler));
            else
                return (moveToHelper(this.getPosition(),world, scheduler));*/
            finalPath = nextPositionHelper(world, target.getPosition());
            //System.out.println(finalPath);
            return (moveToHelper(finalPath.get(1), world, scheduler));
        }
    }
   /* private void transformFull(WorldModel world,
                              EventScheduler scheduler, ImageStore imageStore)
    {
        OCTO_NOT_FULL octo = new OCTO_NOT_FULL(getId(), getResourceLimit(),
                getPosition(), getActionPeriod(), this.getAnimationPeriod(),
                getImages());

        transformHelper(world, scheduler, imageStore, octo);

    }*/

}
