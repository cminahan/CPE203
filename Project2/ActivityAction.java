public class ActivityAction implements Action{
    private final Entity entity;
    private final WorldModel world;
    private final ImageStore imageStore;

    public ActivityAction(Entity entity, WorldModel world, ImageStore imageStore){
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    //getter methods
    public Entity getEntity(){return this.entity;}
    public WorldModel getWorld(){return this.world;}
    public ImageStore getImageStore(){return this.imageStore;}

    //function methods
    public void executeAction(EventScheduler scheduler){
        ((ActivityEntity)this.entity).executeActivity(world, imageStore, scheduler);
    }

    public static ActivityAction createActivityAction(Entity entity, WorldModel world, ImageStore imageStore){
        return new ActivityAction(entity, world, imageStore);
    }


}
