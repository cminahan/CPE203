public class ANIMATION extends Action {

    private AnimationEntity entity;
    private int repeatCount;


    public ANIMATION(AnimationEntity entity, int repeatCount) {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }
    public int getRepeatCount(){ return repeatCount; }

    @Override
    protected void executeAction(EventScheduler scheduler) {
        ANIMATION animation = new ANIMATION(entity, Math.max(getRepeatCount() - 1, 0));
        entity.nextImage();

        if (getRepeatCount() != 1) {
            scheduler.scheduleEvent(entity, animation, (entity).getAnimationPeriod());
        }
    }
}