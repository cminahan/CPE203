import java.util.List;
import java.util.Optional;
import processing.core.PImage;


final class Point
{
   public final int x;
   public final int y;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public String toString()
   {
      return "(" + x + "," + y + ")";
   }

   public boolean equals(Object other)
   {
      return other instanceof Point &&
         ((Point)other).x == this.x &&
         ((Point)other).y == this.y;
   }

   public int hashCode()
   {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

   public boolean adjacent(Point p2)
   {
      return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) ||
              (this.y == p2.y && Math.abs(this.x - p2.x) == 1);
   }

   public Optional<Entity> nearestEntity(List<Entity> entities)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);
         int nearestDistance = this.distanceSquared(nearest.getPosition());

         for (Entity other : entities)
         {
            int otherDistance = this.distanceSquared(other.getPosition());

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

   private int distanceSquared(Point p2)
   {
      int deltaX = this.x - p2.x;
      int deltaY = this.y - p2.y;

      return deltaX * deltaX + deltaY * deltaY;
   }

   public Entity createAtlantis(String id, List<PImage> images)
   {
      return new Entity(EntityKind.ATLANTIS, id, this, images,
              0, 0, 0, 0);
   }

   public Entity createOctoFull(String id, int resourceLimit,
                                       int actionPeriod, int animationPeriod, List<PImage> images)
   {
      return new Entity(EntityKind.OCTO_FULL, id, this, images,
              resourceLimit, resourceLimit, actionPeriod, animationPeriod);
   }

   public Entity createOctoNotFull(String id, int resourceLimit, int actionPeriod, int animationPeriod,
                                   List<PImage> images)
   {
      return new Entity(EntityKind.OCTO_NOT_FULL, id, this, images,
              resourceLimit, 0, actionPeriod, animationPeriod);
   }

   public Entity createObstacle(String id, List<PImage> images)
   {
      return new Entity(EntityKind.OBSTACLE, id, this, images,
              0, 0, 0, 0);
   }

   public Entity createFish(String id, int actionPeriod, List<PImage> images)
   {
      return new Entity(EntityKind.FISH, id, this, images, 0, 0,
              actionPeriod, 0);
   }

   public Entity createCrab(String id, int actionPeriod, int animationPeriod, List<PImage> images)
   {
      return new Entity(EntityKind.CRAB, id, this, images,
              0, 0, actionPeriod, animationPeriod);
   }

   public Entity createQuake(List<PImage> images)
   {
      return new Entity(EntityKind.QUAKE, Functions.QUAKE_ID, this, images,
              0, 0, Functions.QUAKE_ACTION_PERIOD, Functions.QUAKE_ANIMATION_PERIOD);
   }

   public Entity createSgrass(String id,int actionPeriod, List<PImage> images)
   {
      return new Entity(EntityKind.SGRASS, id, this, images, 0, 0,
              actionPeriod, 0);
   }


   }
