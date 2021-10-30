import java.util.List;
import processing.core.PImage;

/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */


public abstract class Entity
{
   private Point position;
   private List<PImage> images;
   private int imageIndex;

   public Entity(Point position,
                 List<PImage> images)
   {
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
   }
   public Point getPosition(){
      return position;
   }
   public void setPosition(Point position){
      this.position = position;
   }
   public List<PImage> getImages(){
      return images;
   }
   protected void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){}
   public PImage getCurrentImage() {
         return this.images.get(this.imageIndex);
   }
   public void nextImage()
   {
      this.imageIndex = (this.imageIndex + 1) % this.images.size();
   }
   public void setImages(List<PImage> im){this.images = im;}

}
