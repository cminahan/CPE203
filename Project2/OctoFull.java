import processing.core.PImage;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class OctoFull implements AnimationEntity, ActivityEntity {
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    private final int resourceCount;
    private final int actionPeriod;
    private final int animationPeriod;

    public OctoFull(String id, Point position, List<PImage> images, int resourceLimit,
                    int actionPeriod, int animationPeriod) {
        this.id = id;
        this.position = position;
        this.imageIndex = 0;
        this.images = images;
        this.resourceCount = resourceLimit;
        this.resourceLimit = resourceLimit;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    //getter methods
    public String getId() {
        return this.id;
    }
    public Point getPosition() {
        return this.position;
    }
    public List<PImage> getImages() {
        return this.images;
    }
    public int getImageIndex() {
        return this.imageIndex;
    }
    public int getResourceLimit() {
        return this.resourceLimit;
    }
    public int getResourceCount() {
        return this.resourceCount;
    }
    public int getActionPeriod() {
        return this.actionPeriod;
    }
    public int getAnimationPeriod() {
        return this.animationPeriod;
    }
    public void setPosition(Point p) {
        this.position = p;
    }

    //function methods
    public void nextImage() {this.imageIndex = (this.imageIndex + 1) % this.images.size();}

    public static OctoFull createOctoFull(String id, int resourceLimit, Point position, int actionPeriod,
                                          int animationPeriod, List<PImage> images) {
        return new OctoFull(id, position, images, resourceLimit, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = findNearest(world, this.position);

        if (fullTarget.isPresent() &&
                this.moveToFull(world, fullTarget.get(), scheduler)) {
            //at atlantis trigger animation
            Atlantis.scheduleActions(fullTarget.get(), scheduler);

            //transform to unfull
            this.transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this,
                    ActivityAction.createActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                ActivityAction.createActivityAction(this, world, imageStore),
                this.actionPeriod);
        scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0),
                this.animationPeriod);
    }

    public Optional<Entity> findNearest(WorldModel world, Point pos)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : world.getEntities())
        {
            if (entity instanceof Atlantis)
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

    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (this.position.adjacent(target.getPosition()))
        {
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

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.getY() - this.position.getY());
            newPos = new Point(this.position.getX(),
                    this.position.getY() + vert);

            if (vert == 0 || world.isOccupied(newPos))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        OctoNotFull octo = OctoNotFull.createOctoNotFull(this.id, this.resourceLimit,
                this.position, this.actionPeriod, this.animationPeriod,
                this.images);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(octo);
        ((ActivityEntity)octo).scheduleActions(scheduler, world, imageStore);
    }
}
