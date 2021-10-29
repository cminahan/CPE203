import java.util.List;
import processing.core.PImage;

final class Background
{
   private final String id;
   private final List<PImage> images;
   public int imageIndex;

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }

   //getter methods
   public List<PImage> getImages(){
      return this.images;
   }

   //Function methods
   public void setBackground(WorldModel world, Point pos)
   {
      if (world.withinBounds(pos))
      {
         world.setBackgroundCell(pos, this);
      }
   }
}
