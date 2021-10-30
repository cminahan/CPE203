import processing.core.PImage;

import java.util.List;

public class ATLANTIS extends executeToRemoveEntities{

    private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

    public ATLANTIS(String id, Point position, List<PImage> images) {
        super(id, position, images, 0, 0);
    }

    @Override
    protected void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        ANIMATION animation = new ANIMATION(this, ATLANTIS_ANIMATION_REPEAT_COUNT);

        scheduler.scheduleEvent(this, animation, getAnimationPeriod());
    }
}
