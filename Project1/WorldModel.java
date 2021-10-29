import processing.core.PImage;

import java.util.*;

/*
WorldModel ideally keeps track of the actual size of our grid world and what is in that world
in terms of entities and background elements
 */

final class WorldModel
{
   private final int numRows;
   private final int numCols;
   private final Background[][] background;
   private final Entity[][] occupancy;
   private final Set<Entity> entities;

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   //getter methods
   public int getNumRows(){return this.numRows;}
   public int getNumCols(){return this.numCols;}
   public Set<Entity> getEntities(){return this.entities;}

   //Function methods
   public boolean transformNotFull(Entity entity, EventScheduler scheduler, ImageStore imageStore)
   {
      if (entity.getResourceCount() >= entity.getResourceLimit())
      {
         Entity octo = entity.getPosition().createOctoFull(entity.getId(), entity.getResourceLimit(),
                 entity.getActionPeriod(), entity.getEntityAnimationPeriod(),
                 entity.getImages());

         entity.removeEntity(this);
         scheduler.unscheduleAllEvents(entity);

         octo.addEntity(this);
         scheduler.scheduleActions(octo, this, imageStore);

         return true;
      }

      return false;
   }

   public boolean moveToNotFull(Entity octo, Entity target, EventScheduler scheduler)
   {
      if (octo.getPosition().adjacent(target.getPosition()))
      {
         octo.increaseResourceCount();
         target.removeEntity(this);
         scheduler.unscheduleAllEvents(target);

         return true;
      }
      else
      {
         Point nextPos = octo.nextPositionOcto(this, target.getPosition());

         if (!octo.getPosition().equals(nextPos))
         {
            Optional<Entity> occupant = this.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            octo.moveEntity(this, nextPos);
         }
         return false;
      }
   }

   public boolean moveToFull(Entity octo, Entity target, EventScheduler scheduler)
   {
      if (octo.getPosition().adjacent(target.getPosition()))
      {
         return true;
      }
      else
      {
         Point nextPos = octo.nextPositionOcto(this, target.getPosition());

         if (!octo.getPosition().equals(nextPos))
         {
            Optional<Entity> occupant = this.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            octo.moveEntity(this, nextPos);
         }
         return false;
      }
   }

   public boolean moveToCrab(Entity crab, Entity target, EventScheduler scheduler)
   {
      if (crab.getPosition().adjacent(target.getPosition()))
      {
         target.removeEntity(this);
         scheduler.unscheduleAllEvents(target);
         return true;
      }
      else
      {
         Point nextPos = crab.nextPositionCrab(this, target.getPosition());

         if (!crab.getPosition().equals(nextPos))
         {
            Optional<Entity> occupant = this.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            crab.moveEntity(this, nextPos);
         }
         return false;
      }
   }

   public Optional<Point> findOpenAround(Point pos)
   {
      for (int dy = -Functions.FISH_REACH; dy <= Functions.FISH_REACH; dy++)
      {
         for (int dx = -Functions.FISH_REACH; dx <= Functions.FISH_REACH; dx++)
         {
            Point newPt = new Point(pos.x + dx, pos.y + dy);
            if (this.withinBounds(newPt) &&
                    !this.isOccupied(newPt))
            {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }

   public void load(Scanner in, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!this.processLine(in.nextLine(), imageStore))
            {
               System.err.println(String.format("invalid entry on line %d",
                       lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line %d",
                    lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }

   private boolean processLine(String line, ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0)
      {
         switch (properties[Functions.PROPERTY_KEY])
         {
            case Functions.BGND_KEY:
               return imageStore.parseBackground(properties, this);
            case Functions.OCTO_KEY:
               return imageStore.parseOcto(properties, this);
            case Functions.OBSTACLE_KEY:
               return imageStore.parseObstacle(properties, this);
            case Functions.FISH_KEY:
               return imageStore.parseFish(properties, this);
            case Functions.ATLANTIS_KEY:
               return imageStore.parseAtlantis(properties, this);
            case Functions.SGRASS_KEY:
               return imageStore.parseSgrass(properties, this);
         }
      }

      return false;
   }

   public boolean withinBounds(Point pos)
   {
      return pos.y >= 0 && pos.y < this.numRows &&
              pos.x >= 0 && pos.x < this.numCols;
   }

   public boolean isOccupied(Point pos)
   {
      return this.withinBounds(pos) &&
              this.getOccupancyCell(pos) != null;
   }

   public Optional<Entity> findNearest(Point pos, EntityKind kind)
   {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : this.entities)
      {
         if (entity.getKind() == kind)
         {
            ofType.add(entity);
         }
      }

      return pos.nearestEntity(ofType);
   }

   public void removeEntityAt(Point pos)
   {
      if (this.withinBounds(pos)
              && this.getOccupancyCell(pos) != null)
      {
         Entity entity = this.getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.position = new Point(-1, -1);
         //entity.getPosition = new Point(-1, -1);
         this.entities.remove(entity);
         this.setOccupancyCell(pos, null);
      }
   }

   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (this.withinBounds(pos))
      {
         return Optional.of(Functions.getCurrentImage(this.getBackgroundCell(pos)));
      }
      else
      {
         return Optional.empty();
      }
   }

   public Optional<Entity> getOccupant(Point pos)
   {
      if (this.isOccupied(pos))
      {
         return Optional.of(this.getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }

   private Entity getOccupancyCell(Point pos)
   {
      return this.occupancy[pos.y][pos.x];
   }

   public void setOccupancyCell(Point pos, Entity entity)
   {
      this.occupancy[pos.y][pos.x] = entity;
   }

   private Background getBackgroundCell(Point pos)
   {
      return this.background[pos.y][pos.x];
   }

   public void setBackgroundCell(Point pos, Background background)
   {
      this.background[pos.y][pos.x] = background;
   }



}
