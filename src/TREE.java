import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TREE extends ActivityOnlyEntity
{
    private static final String EGG_ID_PREFIX = "egg -- ";
    private static final int EGG_CORRUPT_MIN = 20000;
    private static final int EGG_CORRUPT_MAX = 30000;

    private static final Random rand = new Random();


    public TREE(String id, Point position, int actionPeriod, List<PImage> images) {
        super(id, position, images, actionPeriod);
    }

    @Override
    protected void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        ACTIVITY activity = new ACTIVITY(this, world, imageStore);
        Optional<Point> openPt = world.findOpenAround(getPosition());

        if (openPt.isPresent()) {
            EGG egg = new EGG(EGG_ID_PREFIX + getId(),
                    openPt.get(), EGG_CORRUPT_MIN +
                    rand.nextInt(EGG_CORRUPT_MAX - EGG_CORRUPT_MIN),
                    imageStore.getImageList(world.getEggKey()));
            world.addEntity(egg);
            egg.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this, activity, getActionPeriod());
    }
}