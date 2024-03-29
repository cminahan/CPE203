import java.util.List;
import processing.core.PImage;

final class Background
{
   private final String id;
   private final List<PImage> images;
   private int imageIndex;

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }

   //getter methods
   public List<PImage> getImages(){
      return this.images;
   }
   public int getImageIndex() {return this.imageIndex;}

}

