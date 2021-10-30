import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class AnimationActionEntity extends AnimationEntity {


    public AnimationActionEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        ACTIVITY activity = new ACTIVITY(this, world, imageStore);
        ANIMATION animation = new ANIMATION(this, 0);

        scheduler.scheduleEvent(this, activity, getActionPeriod());
        scheduler.scheduleEvent(this, animation, this.getAnimationPeriod());
    }

    public boolean moveToHelper(Point nextPos, WorldModel world, EventScheduler scheduler)
    {
        if (!getPosition().equals(nextPos))
        {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent())
            {
                scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
        }
        return false;
    }

    //public abstract Point nextPosition(WorldModel world, Point destPos);
    //{
       /* int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz, this.getPosition().y);*//*

        //return(nextPositionHelper(horiz, destPos, newPos, world));*/
    //}


    public static boolean neighbors(Point p1, Point p2)
    {
        return p1.x+1 == p2.x && p1.y == p2.y ||
                p1.x-1 == p2.x && p1.y == p2.y ||
                p1.x == p2.x && p1.y+1 == p2.y ||
                p1.x == p2.x && p1.y-1 == p2.y;
    }

    //protected abstract Point nextPositionHelper(int horiz, Point destPos, Point newPos, WorldModel world);

    public boolean adjacent(Point p1, Point p2)
    {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) ||
                (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
    }
}
