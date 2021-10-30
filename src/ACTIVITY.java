public class ACTIVITY extends Action{

    private ActionEntity entity;
    private WorldModel world;
    private ImageStore imageStore;

    public ACTIVITY(ActionEntity entity, WorldModel world, ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }
    public WorldModel getWorld(){ return world; }
    public ImageStore getImageStore(){ return imageStore; }

    @Override
    protected void executeAction(EventScheduler scheduler) {
        entity.executeEntityActivity(getWorld(), getImageStore(), scheduler);
    }
}
