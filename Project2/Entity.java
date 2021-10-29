import java.util.List;
import processing.core.PImage;
/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
*/
public interface Entity{
   String getId();
   Point getPosition();
   List<PImage> getImages();
   int getImageIndex();
   int getAnimationPeriod();
   void setPosition(Point p);

}