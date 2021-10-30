import processing.core.PImage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PTERO extends AnimationActionEntity {

    private static final String QUAKE_KEY = "quake";
    private List<Point> path = new LinkedList<>();
    private PathingStrategy strategy = new SingleStepPathingStrategy();
    //private PathingStrategy strategy = new AStarPathingStrategy();

    public PTERO(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    protected void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        ACTIVITY activity = new ACTIVITY(this, world, imageStore);
        Optional<Entity> eggTarget = world.findNearest(getPosition(), EGG.class);
        long nextPeriod = getActionPeriod();

        if (eggTarget.isPresent()) {
            Point eggPos = eggTarget.get().getPosition();

            if (moveToCrab(world, eggTarget.get(), scheduler)) {
                /*QUAKE quake = new QUAKE(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));*/

                /*world.addEntity(quake);
                nextPeriod += getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);*/
            }
        }

        scheduler.scheduleEvent(this, activity, nextPeriod);
    }

    private boolean moveToCrab(WorldModel world,
                               Entity target, EventScheduler scheduler) {
        if (adjacent(this.getPosition(), target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());
            return (moveToHelper(nextPos, world, scheduler));
        }
    }

    //Override
    public Point nextPosition(WorldModel world, Point destPos) {
        List<Point> points;

        //while (!neighbors(this.getPosition(), destPos)) {
        points = strategy.computePath(this.getPosition(), destPos,
                //p ->  withinBounds(p, grid) && grid[p.y][p.x] != PathingMain.GridValues.OBSTACLE,
                //p -> world.withinBounds(p) && world.getOccupant(p).isPresent() && !(world.getOccupant(p).get().getClass() == FISH.class),
                p -> world.withinBounds(p) && !world.isOccupied(p),
                (p1, p2) -> neighbors(p1, p2),
                PathingStrategy.CARDINAL_NEIGHBORS);
            //DIAGONAL_NEIGHBORS);
            //DIAGONAL_CARDINAL_NEIGHBORS);

        if (points.size() == 0) {
            //System.out.println("No path found");
            return this.getPosition();
        }

        //Point pos = points.get(0);
        //path.add(pos);
        //path.addAll(points);
        //}


        return points.get(0);
        }

  /* @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz, this.getPosition().y);
        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.get().getClass() == FISH.class)))
        {
            int vert = Integer.signum(destPos.y - getPosition().y);
            newPos = new Point(getPosition().x, getPosition().y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.get().getClass() == FISH.class)))
            {
                newPos = getPosition();
            }
        }

        return newPos;
    }*/
}