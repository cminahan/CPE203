import processing.core.PImage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class Dino extends AnimationActionEntity{

    private int resourceCount;
    private int resourceLimit;
    private PathingStrategy strategy = new Dijkstra();

    public Dino(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }
    public int getResourceCount() {return resourceCount;}
    public int getResourceLimit(){ return resourceLimit; }
    public void setResourceCount(int count){
        this.resourceCount = count;
    }

    public List<Point> nextPositionHelper(WorldModel world, Point destPos){
        List<Point> points;

        points = strategy.computePath(this.getPosition(), destPos,
                p ->  world.withinBounds(p) && !world.isOccupied(p),
                (p1, p2) -> neighbors(p1,p2),
                PathingStrategy.CARDINAL_NEIGHBORS);
        return points;
    }



}
