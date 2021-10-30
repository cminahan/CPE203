import processing.core.PImage;

import java.util.List;

public class QUAKE extends executeToRemoveEntities{

    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public QUAKE(Point position, List<PImage> images) {
        super(QUAKE_ID, position, images, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    @Override
    protected void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        ACTIVITY activity = new ACTIVITY(this, world, imageStore);
        ANIMATION animation = new ANIMATION(this, QUAKE_ANIMATION_REPEAT_COUNT);

        scheduler.scheduleEvent(this, activity, getActionPeriod());
        scheduler.scheduleEvent(this, animation, this.getAnimationPeriod());
    }
}