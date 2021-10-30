import processing.core.PImage;

import java.util.List;

public abstract class ActionEntity extends Entity{

    String id;
    int actionPeriod;


    public ActionEntity(String id, Point position, List<PImage> images, int actionPeriod) {
        super(position, images);
        this.id = id;
        this.actionPeriod = actionPeriod;

    }

    public String getId(){ return id; }
    public int getActionPeriod(){
        return actionPeriod;
    }

    protected abstract void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

}
