import java.util.List;
import java.util.Optional;
import processing.core.PImage;


final class Point
{
   private final int x;
   private final int y;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   //getter methods
   public int getX(){ return this.x;}
   public int getY(){return this.y;}

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

   public int distanceSquared( Point p2)
   {
      int deltaX = this.getX() - p2.getX();
      int deltaY = this.getY() - p2.getY();

      return deltaX * deltaX + deltaY * deltaY;
   }

   public boolean adjacent(Point p2)
   {
      return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) ||
              (this.y == p2.y && Math.abs(this.x - p2.x) == 1);
   }


}

