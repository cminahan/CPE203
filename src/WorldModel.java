import processing.core.PImage;

import java.util.*;

/*
WorldModel ideally keeps track of the actual size of our grid world and what is in that world
in terms of entities and background elements
 */

final class WorldModel
{
   private int numRows;
   private int numCols;
   private Background background[][];
   private Entity occupancy[][];
   private Set<Entity> entities;

   private static final String DINO_KEY = "dino";
   private static final int DINO_NUM_PROPERTIES = 7;
   private static final int DINO_ID = 1;
   private static final int DINO_COL = 2;
   private static final int DINO_ROW = 3;
   private static final int DINO_LIMIT = 4;
   private static final int DINO_ACTION_PERIOD = 5;
   private static final int DINO_ANIMATION_PERIOD = 6;

   private static final String OBSTACLE_KEY = "obstacle";
   private static final int OBSTACLE_NUM_PROPERTIES = 4;
   private static final int OBSTACLE_ID = 1;
   private static final int OBSTACLE_COL = 2;
   private static final int OBSTACLE_ROW = 3;

   private static final String EGG_KEY = "egg";
   private static final int EGG_NUM_PROPERTIES = 5;
   private static final int EGG_ID = 1;
   private static final int EGG_COL = 2;
   private static final int EGG_ROW = 3;
   private static final int EGG_ACTION_PERIOD = 4;

   private static final String ATLANTIS_KEY = "atlantis";
   private static final int ATLANTIS_NUM_PROPERTIES = 4;
   private static final int ATLANTIS_ID = 1;
   private static final int ATLANTIS_COL = 2;
   private static final int ATLANTIS_ROW = 3;

   private static final String TREE_KEY = "tree";
   private static final int TREE_NUM_PROPERTIES = 5;
   private static final int TREE_ID = 1;
   private static final int TREE_COL = 2;
   private static final int TREE_ROW = 3;
   private static final int TREE_ACTION_PERIOD = 4;

   private static final String TRICERA_KEY = "tricera";
   private static final int TRICERA_NUM_PROPERTIES = 7;
   private static final int TRICERA_ID = 1;
   private static final int TRICERA_COL = 2;
   private static final int TRICERA_ROW = 3;
   private static final int TRICERA_LIMIT = 4;
   private static final int TRICERA_ACTION_PERIOD = 5;
   private static final int TRICERA_ANIMATION_PERIOD = 6;

   private static final int EGG_REACH = 1;

   private static final String BGND_KEY = "background";
   private static final int BGND_NUM_PROPERTIES = 4;
   private static final int BGND_ID = 1;
   private static final int BGND_COL = 2;
   private static final int BGND_ROW = 3;

   private static final int PROPERTY_KEY = 0;

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
   public int getNumRows(){
      return numRows;
   }
   public int getNumCols(){
      return numCols;
   }
   public String getEggKey(){
      return EGG_KEY;
   }
   public Set<Entity> getEntities(){
      return entities;
   }
   //21
   public void addEntity(Entity entity)
   {
      if (withinBounds(entity.getPosition()))
      {
         setOccupancyCell(entity.getPosition(), entity);
         this.entities.add(entity);
      }
   }
   //22
   public void removeEntity(Entity entity)
   {
      removeEntityAt(entity.getPosition());
   }
   //23
   public void tryAddEntity(Entity entity)
   {
      if (isOccupied(entity.getPosition()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      addEntity(entity);
   }
   //24
   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (withinBounds(pos))
      {
         return Optional.of(getBackgroundCell(pos).getCurrentImage());
      }
      else
      {
         return Optional.empty();
      }
   }
   //27
   public void removeEntityAt(Point pos)
   {
      if (withinBounds(pos)
              && getOccupancyCell(pos) != null)
      {
         Entity entity = getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         this.entities.remove(entity);
         setOccupancyCell(pos, null);
      }
   }
   //28
   public void setBackground(Point pos, Background background)
   {
      if (withinBounds(pos))
      {
         setBackgroundCell(pos, background);
      }
   }
   //29
   private boolean parseBackground(String [] properties, ImageStore imageStore)
   {
      if (properties.length == BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                 Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         setBackground(pt, new Background(id, imageStore.getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }
   //30
   private boolean parseDINO(String [] properties, ImageStore imageStore)
   {
      if (properties.length == DINO_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[DINO_COL]),
                 Integer.parseInt(properties[DINO_ROW]));
         DINO_NOT_FULL dino_not_full = new DINO_NOT_FULL(properties[DINO_ID],
                 Integer.parseInt(properties[DINO_LIMIT]),
                 pt,
                 Integer.parseInt(properties[DINO_ACTION_PERIOD]),
                 Integer.parseInt(properties[DINO_ANIMATION_PERIOD]),
                 imageStore.getImageList(DINO_KEY));
         tryAddEntity(dino_not_full);
      }

      return properties.length == DINO_NUM_PROPERTIES;
   }

   private boolean parseTRICERA(String [] properties, ImageStore imageStore)
   {
      if (properties.length == TRICERA_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[TRICERA_COL]),
                 Integer.parseInt(properties[TRICERA_ROW]));
         TRICERA tricera = new TRICERA(properties[TRICERA_ID],
                 Integer.parseInt(properties[TRICERA_LIMIT]),
                 pt,
                 Integer.parseInt(properties[TRICERA_ACTION_PERIOD]),
                 Integer.parseInt(properties[TRICERA_ANIMATION_PERIOD]),
                 imageStore.getImageList(TRICERA_KEY));
         tryAddEntity(tricera);
      }

      return properties.length == TRICERA_NUM_PROPERTIES;
   }

   //31
   private boolean parseObstacle(String [] properties, ImageStore imageStore)
   {
      if (properties.length == OBSTACLE_NUM_PROPERTIES)
      {
         Point pt = new Point(
                 Integer.parseInt(properties[OBSTACLE_COL]),
                 Integer.parseInt(properties[OBSTACLE_ROW]));
         OBSTACLE obstacle = new OBSTACLE(properties[OBSTACLE_ID],
                 pt, imageStore.getImageList(OBSTACLE_KEY));
         tryAddEntity(obstacle);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }
   //32
   private boolean parseEGG(String [] properties, ImageStore imageStore)
   {
      if (properties.length == EGG_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[EGG_COL]),
                 Integer.parseInt(properties[EGG_ROW]));
         EGG egg = new EGG(properties[EGG_ID],
                 pt, Integer.parseInt(properties[EGG_ACTION_PERIOD]),
                 imageStore.getImageList(EGG_KEY));
         tryAddEntity(egg);
      }

      return properties.length == EGG_NUM_PROPERTIES;
   }
   //33
   private boolean parseAtlantis(String [] properties, ImageStore imageStore)
   {
      if (properties.length == ATLANTIS_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[ATLANTIS_COL]),
                 Integer.parseInt(properties[ATLANTIS_ROW]));
         ATLANTIS atlantis = new ATLANTIS(properties[ATLANTIS_ID],
                 pt, imageStore.getImageList(ATLANTIS_KEY));
         tryAddEntity(atlantis);
      }

      return properties.length == ATLANTIS_NUM_PROPERTIES;
   }
   //34
   private boolean parseTREE(String [] properties, ImageStore imageStore)
   {
      if (properties.length == TREE_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[TREE_COL]),
                 Integer.parseInt(properties[TREE_ROW]));
         TREE tree = new TREE(properties[TREE_ID],
                 pt,
                 Integer.parseInt(properties[TREE_ACTION_PERIOD]),
                 imageStore.getImageList(TREE_KEY));
         tryAddEntity(tree);
      }

      return properties.length == TREE_NUM_PROPERTIES;
   }
   //35
   private boolean processLine(String line, ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0)
      {
         switch (properties[PROPERTY_KEY])
         {
            case BGND_KEY:
               return parseBackground(properties, imageStore);
            case DINO_KEY:
               return parseDINO(properties, imageStore);
            case OBSTACLE_KEY:
               return parseObstacle(properties, imageStore);
            case EGG_KEY:
               return parseEGG(properties, imageStore);
            case ATLANTIS_KEY:
               return parseAtlantis(properties, imageStore);
            case TREE_KEY:
               return parseTREE(properties, imageStore);
            case TRICERA_KEY:
               return parseTRICERA(properties, imageStore);
         }
      }

      return false;
   }
   //36
   public void load(Scanner in, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!processLine(in.nextLine(), imageStore))
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
   //42
   public Optional<Point> findOpenAround(Point pos)
   {
      for (int dy = -EGG_REACH; dy <= EGG_REACH; dy++)
      {
         for (int dx = -EGG_REACH; dx <= EGG_REACH; dx++)
         {
            Point newPt = new Point(pos.x + dx, pos.y + dy);
            if (withinBounds(newPt) &&
                    !isOccupied(newPt))
            {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }
   //44
   public boolean withinBounds(Point pos)
   {
      return pos.y >= 0 && pos.y < this.numRows &&
              pos.x >= 0 && pos.x < this.numCols;
   }
   //48
   private Entity getOccupancyCell(Point pos)
   {
      return this.occupancy[pos.y][pos.x];
   }
   //49
   public boolean isOccupied(Point pos)
   {
      return withinBounds(pos) &&
              getOccupancyCell(pos) != null;
   }
   //50
   public Optional<Entity> getOccupant(Point pos)
   {
      if (isOccupied(pos))
      {
         return Optional.of(getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }
   //51
   public void setOccupancyCell(Point pos, Entity entity)
   {
      this.occupancy[pos.y][pos.x] = entity;
   }
   //52
   private Background getBackgroundCell(Point pos)
   {
      return this.background[pos.y][pos.x];
   }
   //53
   private void setBackgroundCell(Point pos, Background background)
   {
      this.background[pos.y][pos.x] = background;
   }
   //57
   public Optional<Entity> findNearest(Point pos, Class kind)
   {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : this.entities)
      {
         if (entity.getClass() == kind)
         /*if (entity.getKind() == kind)*/
         {
            ofType.add(entity);
         }
      }

      return nearestEntity(ofType, pos);
   }
   //58
   private static Optional<Entity> nearestEntity(List<Entity> entities,
                                                Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);
         int nearestDistance = distanceSquared(nearest.getPosition(), pos);

         for (Entity other : entities)
         {
            int otherDistance = distanceSquared(other.getPosition(), pos);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }
   //59
   private static int distanceSquared(Point p1, Point p2)
   {
      int deltaX = p1.x - p2.x;
      int deltaY = p1.y - p2.y;

      return deltaX * deltaX + deltaY * deltaY;
   }

   public void moveEntity(Entity entity, Point pos)
   {
      Point oldPos = entity.getPosition();
      if (this.withinBounds(pos) && !pos.equals(oldPos))
      {
         setOccupancyCell(oldPos, null);
         removeEntityAt(pos);
         setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }
}
