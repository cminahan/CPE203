public class AnimationAction implements Action {
    private final Entity entity;
    private final int repeatCount;

    public AnimationAction(Entity entity, int repeatCount) {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    //getter methods
    public Entity getEntity(){return this.entity;}
    //public int getRepeatCount(){return this.repeatCount;}

    //function methods
    public static AnimationAction createAnimationAction(Entity entity, int repeatCount) {
        return new AnimationAction(entity, repeatCount);
    }

    public void executeAction(EventScheduler scheduler) {
        ((AnimationEntity)this.entity).nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.entity,
                    createAnimationAction(this.entity,
                            Math.max(this.repeatCount - 1, 0)),
                    (this.entity).getAnimationPeriod());
        }
    }
}
