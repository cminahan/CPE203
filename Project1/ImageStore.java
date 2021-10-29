import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

/*
ImageStore: to ideally keep track of the images used in our virtual world
 */

final class ImageStore
{
   private final Map<String, List<PImage>> images;
   private final List<PImage> defaultImages;

   public ImageStore(PImage defaultImage)
   {
      this.images = new HashMap<>();
      defaultImages = new LinkedList<>();
      defaultImages.add(defaultImage);
   }

   //getter methods
   public List<PImage> getImageList(String key)
   {
      return this.images.getOrDefault(key, this.defaultImages);
   }

   //Function methods
   public void loadImages(Scanner in, PApplet screen)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            Functions.processImageLine(this.images, in.nextLine(), screen);
         }
         catch (NumberFormatException e)
         {
            System.out.println(String.format("Image format error on line %d",
                    lineNumber));
         }
         lineNumber++;
      }
   }

   public boolean parseBackground(String [] properties, WorldModel world)
   {
      if (properties.length == Functions.BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[Functions.BGND_COL]),
                 Integer.parseInt(properties[Functions.BGND_ROW]));
         String id = properties[Functions.BGND_ID];
         new Background(id, this.getImageList(id)).setBackground(world, pt);
      }

      return properties.length == Functions.BGND_NUM_PROPERTIES;
   }

   public boolean parseOcto(String [] properties, WorldModel world)
   {
      if (properties.length == Functions.OCTO_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[Functions.OCTO_COL]),
                 Integer.parseInt(properties[Functions.OCTO_ROW]));
         Entity entity = pt.createOctoNotFull(properties[Functions.OCTO_ID],
                 Integer.parseInt(properties[Functions.OCTO_LIMIT]),
                 Integer.parseInt(properties[Functions.OCTO_ACTION_PERIOD]),
                 Integer.parseInt(properties[Functions.OCTO_ANIMATION_PERIOD]),
                 this.getImageList(Functions.OCTO_KEY));
         entity.tryAddEntity(world);
      }

      return properties.length == Functions.OCTO_NUM_PROPERTIES;
   }

   public boolean parseObstacle(String [] properties, WorldModel world)
   {
      if (properties.length == Functions.OBSTACLE_NUM_PROPERTIES)
      {
         Point pt = new Point(
                 Integer.parseInt(properties[Functions.OBSTACLE_COL]),
                 Integer.parseInt(properties[Functions.OBSTACLE_ROW]));
         Entity entity = pt.createObstacle(properties[Functions.OBSTACLE_ID],
                 this.getImageList(Functions.OBSTACLE_KEY));
         entity.tryAddEntity(world);
      }

      return properties.length == Functions.OBSTACLE_NUM_PROPERTIES;
   }

   public boolean parseFish(String [] properties, WorldModel world)
   {
      if (properties.length == Functions.FISH_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[Functions.FISH_COL]),
                 Integer.parseInt(properties[Functions.FISH_ROW]));
         Entity entity = pt.createFish(properties[Functions.FISH_ID],
                 Integer.parseInt(properties[Functions.FISH_ACTION_PERIOD]),
                 this.getImageList(Functions.FISH_KEY));
         entity.tryAddEntity(world);
      }

      return properties.length == Functions.FISH_NUM_PROPERTIES;
   }

   public boolean parseAtlantis(String [] properties, WorldModel world)
   {
      if (properties.length == Functions.ATLANTIS_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[Functions.ATLANTIS_COL]),
                 Integer.parseInt(properties[Functions.ATLANTIS_ROW]));
         Entity entity = pt.createAtlantis(properties[Functions.ATLANTIS_ID],
                  this.getImageList(Functions.ATLANTIS_KEY));
         entity.tryAddEntity(world);
      }

      return properties.length == Functions.ATLANTIS_NUM_PROPERTIES;
   }

   public boolean parseSgrass(String [] properties, WorldModel world)
   {
      if (properties.length == Functions.SGRASS_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[Functions.SGRASS_COL]),
                 Integer.parseInt(properties[Functions.SGRASS_ROW]));
         Entity entity = pt.createSgrass(properties[Functions.SGRASS_ID],
                 Integer.parseInt(properties[Functions.SGRASS_ACTION_PERIOD]),
                 this.getImageList(Functions.SGRASS_KEY));
         entity.tryAddEntity(world);
      }

      return properties.length == Functions.SGRASS_NUM_PROPERTIES;
   }

}
