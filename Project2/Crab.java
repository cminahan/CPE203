import processing.core.PImage;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Crab implements ActivityEntity, AnimationEntity{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int resourceCount;
    private final int resourceLimit;
    private final int animationPeriod;
    private final int actionPeriod;

    public Crab(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = 0;
        this.resourceCount = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    //getter methods
    public String getId(){return this.id;}
    public Point getPosition(){return this.position;}
    public List<PImage> getImages(){return this.images;}
    public int getResourceCount(){return this.resourceCount;}
    public int getResourceLimit(){return this.resourceLimit;}
    public int getAnimationPeriod(){return this.animationPeriod;}
    public int getActionPeriod(){return this.actionPeriod;}
    public void nextImage(){this.imageIndex = (this.imageIndex + 1) % this.images.size();}
    public int getImageIndex(){return this.imageIndex;}
    public void setPosition(Point p){this.position = p;}

    //function methods
    public static Crab createCrab(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Crab(id, position, images, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> crabTarget = findNearest(world, this.position);
        long nextPeriod = this.actionPeriod;

        if (crabTarget.isPresent())
        {
            Point tgtPos = crabTarget.get().getPosition();

            if (this.moveToCrab(world, crabTarget.get(), scheduler))
            {
                ActivityEntity quake = Quake.createQuake(tgtPos,
                        imageStore.getImageList(Functions.QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.actionPeriod;
                (quake).scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                ActivityAction.createActivityAction(this, world, imageStore),
                nextPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                ActivityAction.createActivityAction( this, world, imageStore),
                this.actionPeriod);
        scheduler.scheduleEvent(this,
                AnimationAction.createAnimationAction(this, 0), this.getAnimationPeriod());
    }

    public Optional<Entity> findNearest(WorldModel world, Point pos)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : world.getEntities())
        {
            if (entity instanceof SGrass)
            {
                ofType.add(entity);
            }
        }

        return nearestEntity(ofType, pos);
    }

    public Optional<Entity> nearestEntity(List<Entity> entities, Point pos)
    {
        if (entities.isEmpty())
        {
            return Optional.empty();
        }
        else
        {
            Entity nearest = entities.get(0);
            int nearestDistance = pos.distanceSquared(nearest.getPosition());

            for (Entity other : entities)
            {
                int otherDistance = pos.distanceSquared(other.getPosition());

                if (otherDistance < nearestDistance)
                {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    public boolean moveToCrab(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (this.position.adjacent(target.getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                occupant.ifPresent(scheduler::unscheduleAllEvents);
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public Point nextPosition(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - this.position.getX());
        Point newPos = new Point(this.position.getX() + horiz,
                this.position.getY());

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.get() instanceof Fish)))
        {
            int vert = Integer.signum(destPos.getY() - this.position.getY());
            newPos = new Point(this.position.getX(), this.position.getY() + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.get() instanceof Fish)))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }
}
