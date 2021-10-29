import java.util.List;
import processing.core.PImage;
import java.util.*;

/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */


final class Entity
{
   private final EntityKind kind;
   private final String id;
   public Point position;
   private final List<PImage> images;
   private int imageIndex;
   private final int resourceLimit;
   private int resourceCount;
   private final int actionPeriod;
   private final int animationPeriod;



   public Entity(EntityKind kind, String id, Point position,
                 List<PImage> images, int resourceLimit, int resourceCount,
                 int actionPeriod, int animationPeriod)
   {
      this.kind = kind;
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
      this.resourceLimit = resourceLimit;
      this.resourceCount = resourceCount;
      this.actionPeriod = actionPeriod;
      this.animationPeriod = animationPeriod;
   }

   //getter methods
   public EntityKind getKind(){
      return this.kind;
   }
   public Point getPosition(){return this.position;}
   public List<PImage> getImages(){
      return this.images;
   }
   public String getId(){
      return this.id;
   }
   public int getImageIndex(){
      return this.imageIndex;
   }
   public int getResourceLimit(){
      return this.resourceLimit;
   }
   public int getResourceCount(){
      return this.resourceCount;
   }
   public int getActionPeriod() {
      return this.actionPeriod;
   }
   public int getEntityAnimationPeriod(){
      return this.animationPeriod;
   }
   public void increaseResourceCount(){
      this.resourceCount++;
   }

   //Function methods
   public int getAnimationPeriod()
   {
      switch (this.kind)
      {
         case OCTO_FULL:
         case OCTO_NOT_FULL:
         case CRAB:
         case QUAKE:
         case ATLANTIS:
            return this.animationPeriod;
         default:
            throw new UnsupportedOperationException(
                    String.format("getAnimationPeriod not supported for %s",
                            this.kind));
      }
   }

   public void nextImage()
   {
      this.imageIndex = (this.imageIndex + 1) % this.images.size();
   }

   public void executeOctoFullActivity(WorldModel world,
                                       ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> fullTarget = world.findNearest(this.position,
              EntityKind.ATLANTIS);

      if (fullTarget.isPresent() &&
              world.moveToFull(this, fullTarget.get(), scheduler))
      {
         //at atlantis trigger animation
         scheduler.scheduleActions(fullTarget.get(), world, imageStore);

         //transform to unfull
         this.transformFull(world, scheduler, imageStore);
      }
      else
      {
         scheduler.scheduleEvent(this,
                 this.createActivityAction(world, imageStore),
                 this.actionPeriod);
      }
   }

   public void executeOctoNotFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> notFullTarget = world.findNearest(this.position,
              EntityKind.FISH);

      if (!notFullTarget.isPresent() ||
              !world.moveToNotFull(this, notFullTarget.get(), scheduler) ||
              !world.transformNotFull(this, scheduler, imageStore))
      {
         scheduler.scheduleEvent(this,
                 this.createActivityAction(world, imageStore),
                 this.actionPeriod);
      }
   }

   public void executeFishActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      Point pos = this.position;  // store current position before removing

      this.removeEntity(world);
      scheduler.unscheduleAllEvents(this);

      Entity crab = pos.createCrab(this.id + Functions.CRAB_ID_SUFFIX,
              this.actionPeriod / Functions.CRAB_PERIOD_SCALE,
              Functions.CRAB_ANIMATION_MIN +
                      Functions.rand.nextInt(Functions.CRAB_ANIMATION_MAX - Functions.CRAB_ANIMATION_MIN),
              imageStore.getImageList(Functions.CRAB_KEY));

      crab.addEntity(world);
      scheduler.scheduleActions(crab, world, imageStore);
   }

   public void executeCrabActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> crabTarget = world.findNearest(this.position, EntityKind.SGRASS);
      long nextPeriod = this.actionPeriod;

      if (crabTarget.isPresent())
      {
         Point tgtPos = crabTarget.get().position;

         if (world.moveToCrab(this, crabTarget.get(), scheduler))
         {
            Entity quake = tgtPos.createQuake(imageStore.getImageList(Functions.QUAKE_KEY));

            quake.addEntity(world);
            nextPeriod += this.actionPeriod;
            scheduler.scheduleActions(quake, world, imageStore);
         }
      }

      scheduler.scheduleEvent(this,
              this.createActivityAction(world, imageStore), nextPeriod);
   }

   public void executeQuakeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      scheduler.unscheduleAllEvents(this);
      this.removeEntity(world);
   }

   public void executeAtlantisActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      scheduler.unscheduleAllEvents(this);
      this.removeEntity(world);
   }

   public void executeSgrassActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Point> openPt = world.findOpenAround(this.position);

      if (openPt.isPresent())
      {
         Entity fish = openPt.get().createFish(Functions.FISH_ID_PREFIX + this.id, Functions.FISH_CORRUPT_MIN +
                         Functions.rand.nextInt(Functions.FISH_CORRUPT_MAX - Functions.FISH_CORRUPT_MIN),
                 imageStore.getImageList(Functions.FISH_KEY));
         fish.addEntity(world);
         scheduler.scheduleActions(fish, world, imageStore);
      }

      scheduler.scheduleEvent(this,
              this.createActivityAction(world, imageStore),
              this.actionPeriod);
   }

   private void transformFull(WorldModel world,
                                    EventScheduler scheduler, ImageStore imageStore)
   {
      Entity octo = this.position.createOctoNotFull(this.id, this.resourceLimit,
              this.actionPeriod, this.animationPeriod, this.images);

      this.removeEntity(world);
      scheduler.unscheduleAllEvents(this);

      octo.addEntity(world);
      scheduler.scheduleActions(octo, world, imageStore);
   }

   public Point nextPositionOcto(WorldModel world, Point destPos)
   {
      int horiz = Integer.signum(destPos.x - this.position.x);
      Point newPos = new Point(this.position.x + horiz,
              this.position.y);

      if (horiz == 0 || world.isOccupied(newPos))
      {
         int vert = Integer.signum(destPos.y - this.position.y);
         newPos = new Point(this.position.x,
                 this.position.y + vert);

         if (vert == 0 || world.isOccupied(newPos))
         {
            newPos = this.position;
         }
      }

      return newPos;
   }

   public Point nextPositionCrab(WorldModel world, Point destPos)
   {
      int horiz = Integer.signum(destPos.x - this.position.x);
      Point newPos = new Point(this.position.x + horiz,
              this.position.y);

      Optional<Entity> occupant = world.getOccupant(newPos);

      if (horiz == 0 ||
              (occupant.isPresent() && !(occupant.get().kind == EntityKind.FISH)))
      {
         int vert = Integer.signum(destPos.y - this.position.y);
         newPos = new Point(this.position.x, this.position.y + vert);
         occupant = world.getOccupant(newPos);

         if (vert == 0 ||
                 (occupant.isPresent() && !(occupant.get().kind == EntityKind.FISH)))
         {
            newPos = this.position;
         }
      }

      return newPos;
   }

   public void tryAddEntity(WorldModel world)
   {
      if (world.isOccupied(this.position))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      this.addEntity(world);
   }

   public void addEntity(WorldModel world)
   {
      if (world.withinBounds(this.position))
      {
         world.setOccupancyCell(this.position, this);
         world.getEntities().add(this);
      }
   }

   public void moveEntity(WorldModel world, Point pos)
   {
      Point oldPos = this.position;
      if (world.withinBounds(pos) && !pos.equals(oldPos))
      {
         world.setOccupancyCell(oldPos, null);
         world.removeEntityAt(pos);
         world.setOccupancyCell(pos, this);
         this.position = pos;
      }
   }

   public void removeEntity(WorldModel world)
   {
      world.removeEntityAt(this.position);
   }

   public Action createAnimationAction(int repeatCount)
   {
      return new Action(ActionKind.ANIMATION, this, null, null, repeatCount);
   }

   public Action createActivityAction(WorldModel world, ImageStore imageStore)
   {
      return new Action(ActionKind.ACTIVITY, this, world, imageStore, 0);
   }

}
