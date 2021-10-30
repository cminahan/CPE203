import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import processing.core.*;
import processing.opengl.PGraphicsOpenGL;

/*
VirtualWorld is our main wrapper
It keeps track of data necessary to use Processing for drawing but also keeps track of the necessary
components to make our world run (eventScheduler), the data in our world (WorldModel) and our
current view (think virtual camera) into that world (WorldView)
 */

public final class VirtualWorld
   extends PApplet
{
   private static final int TIMER_ACTION_PERIOD = 100;

   private static final int VIEW_WIDTH = 1000;
   private static final int VIEW_HEIGHT = 1400;
   private static final int TILE_WIDTH = 25;
   private static final int TILE_HEIGHT = 25;
   private static final int WORLD_WIDTH_SCALE = 2;
   private static final int WORLD_HEIGHT_SCALE = 2;

   private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
   private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
   private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
   private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

   private static final String IMAGE_LIST_FILE_NAME = "imagelist";
   private static final String DEFAULT_IMAGE_NAME = "background_default";
   private static final int DEFAULT_IMAGE_COLOR = 0x808080;

   private static final String LOAD_FILE_NAME = "world.sav";

   private static final String FAST_FLAG = "-fast";
   private static final String FASTER_FLAG = "-faster";
   private static final String FASTEST_FLAG = "-fastest";
   private static final double FAST_SCALE = 0.5;
   private static final double FASTER_SCALE = 0.25;
   private static final double FASTEST_SCALE = 0.10;

   private static double timeScale = 1.0;

   private ImageStore imageStore;
   private WorldModel world;
   private WorldView view;
   private EventScheduler scheduler;

   private long next_time;
   private MAIN_CHAR main_char;

   private boolean start = true;
   private boolean redraw = false;
   int clickX;
   int clickY;

   private static final String PTERO_KEY = "ptero";
   private static final String PTERO_ID_SUFFIX = " -- ptero";
   private static final int PTERO_PERIOD_SCALE = 4;
   private static final int PTERO_ANIMATION_MIN = 50;
   private static final int PTERO_ANIMATION_MAX = 150;

   private static final Random rand = new Random();

   public void settings()
   {
      size(VIEW_WIDTH, VIEW_HEIGHT);
   }

   /*
      Processing entry point for "sketch" setup.
   */
   public void setup()
   {
      this.imageStore = new ImageStore(
         createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
      this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
         createDefaultBackground(imageStore));
      this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,
         TILE_WIDTH, TILE_HEIGHT);
      this.scheduler = new EventScheduler(timeScale);

      loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
      loadWorld(world, LOAD_FILE_NAME, imageStore);

      scheduleActions(world, scheduler, imageStore);

      next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;

      this.main_char = new MAIN_CHAR("mainChar", 6, new Point(10,10),  888, 1, imageStore.getImageList("mainChar"));
      world.tryAddEntity(main_char);
   }

   public void draw()
   {
      long time = System.currentTimeMillis();
      if (time >= next_time)
      {
         this.scheduler.updateOnTime(time);
         next_time = time + TIMER_ACTION_PERIOD;
      }
      view.drawViewport();
   }
   public void mousePressed(){
      redraw = true;
      redraw = true;
      clickX = mouseX;
      clickY = mouseY;

      int gameX;
      int gameY;
      int nextX = clickX+25;
      int nextoth = clickX - 25;
      gameX = clickX/25;
      gameY = clickY/25;
      nextX = nextX/25;
      int x = (int)(Math.random()*10);
      int y = (int)(Math.random()*10);
      Point entityPoint = new Point(gameX + x, gameY + y);

      Point gamePoint = new Point(gameX, gameY);
      if (!(world.isOccupied(gamePoint)) && !(world.isOccupied(new Point(nextX, gameY)))){
         world.setBackground(gamePoint,
                 new Background("hotLava", imageStore.getImageList("hotLava")));
         world.setBackground(new Point(nextX, gameY),
                 new Background("volcano", imageStore.getImageList("volcano")));
         world.setBackground(new Point((gameX - 25)/25, gameY),
                 new Background("volcano", imageStore.getImageList("volcano")));
         PTERO ptero = new PTERO(main_char.getId() + PTERO_ID_SUFFIX,
                 entityPoint, 500, PTERO_ANIMATION_MIN +
                 rand.nextInt(PTERO_ANIMATION_MAX - PTERO_ANIMATION_MIN),
                 imageStore.getImageList(PTERO_KEY));
         world.addEntity(ptero);
         ptero.scheduleActions(scheduler, world, imageStore);

         List<Entity> dinos = new LinkedList<>();
         for (Entity e : world.getEntities()){
            if(e.getClass().equals(DINO_NOT_FULL.class) && e.getImages() != imageStore.getImageList("newDino"))
               dinos.add(e);
         }
         if(!dinos.isEmpty()){
            dinos.get(0).setImages(imageStore.getImageList("newDino"));
         }

         /*Optional <Entity> newDino = world.findNearest(gamePoint, DINO_NOT_FULL.class);
         if(newDino.isPresent()){
            newDino.setImages(imageStore.getImageList("newDino"));
         }*/
      }

      //Point treePoint = new Point(gamePoint.x-3, gamePoint.y-4);
      if (world.isOccupied(gamePoint)){
         Entity entity = world.getOccupant(gamePoint).get();
         world.removeEntityAt(gamePoint);
         world.removeEntityAt(new Point(nextX, gameY));
         world.removeEntityAt(new Point((gameX - 25)/25, gameY));
         scheduler.unscheduleAllEvents(entity);
      }
   }

   public void keyPressed()
   {
      if (key == CODED)
      {
         int mainX = main_char.getPosition().x;
         int mainY = main_char.getPosition().y;
         int dx = 0;
         int dy = 0;
         int newX = 0;
         int newY = 0;

         switch (keyCode)
         {
            case UP:
               dy = -1;
               newX = mainX;
               newY = mainY - 1;
               break;
            case DOWN:
               dy = 1;
               newX = mainX;
               newY = mainY + 1;
               break;
            case LEFT:
               dx = -1;
               newX = mainX - 1;
               newY = mainY;
               break;
            case RIGHT:
               dx = 1;
               newX = mainX + 1;
               newY = mainY;
               break;
         }
         main_char.scheduleActions(scheduler, world, imageStore);
         //view.shiftView(dx, dy);
         System.out.println(world.isOccupied(new Point(newX, newY)));
         if (world.isOccupied(new Point(newX, newY))){
            System.out.println(world.getOccupant(new Point(newX,newY)).get().getClass());
         }

         if (world.isOccupied(new Point(newX, newY))){
            Class type = world.getOccupant(new Point(newX, newY)).get().getClass();
            if (type == TREE.class || type == OBSTACLE.class)
               this.main_char.setPosition(main_char.getPosition());
            else if (type == DINO_NOT_FULL.class) {
               world.removeEntityAt(new Point(mainX, mainX));
               //System.out.println("you dead");
               /*int cols = view.getViewPort().getNumCols();
               int rows = view.getViewPort().getNumRows();
               for(int x = 0; x < rows; x++){
                  for(int y = 0; y < cols; y++){
                     world.setBackground(new Point(x,y),
                             new Background("lava", imageStore.getImageList("lava")));
                  }
               }*/
            }
            else if (type == EGG.class) {
               Optional<Entity> egg = world.getOccupant(new Point(newX, newY));
               world.removeEntity(egg.get());
               System.out.println("YOU GOT AN EGG!");
            }
         }
         else
            this.main_char.setPosition(new Point(newX, newY));
      }
   }



   private static Background createDefaultBackground(ImageStore imageStore)
   {
      return new Background(DEFAULT_IMAGE_NAME,
         imageStore.getImageList(DEFAULT_IMAGE_NAME));
   }

   private static PImage createImageColored(int width, int height, int color)
   {
      PImage img = new PImage(width, height, RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         img.pixels[i] = color;
      }
      img.updatePixels();
      return img;
   }

   private static void loadImages(String filename, ImageStore imageStore,
      PApplet screen)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         imageStore.loadImages(in, screen);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   private static void loadWorld(WorldModel world, String filename,
      ImageStore imageStore)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         world.load(in, imageStore);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   private static void scheduleActions(WorldModel world,
      EventScheduler scheduler, ImageStore imageStore)
   {
      for (Entity entity : world.getEntities())
      {
         //Only start actions for entities that include action (not those with just animations)
         if (ActionEntity.class.isInstance(entity)) {
            if(((ActionEntity)entity).getActionPeriod() > 0)

            entity.scheduleActions(scheduler, world, imageStore);
         }
      }
   }

   private static void parseCommandLine(String [] args)
   {
      for (String arg : args)
      {
         switch (arg)
         {
            case FAST_FLAG:
               timeScale = Math.min(FAST_SCALE, timeScale);
               break;
            case FASTER_FLAG:
               timeScale = Math.min(FASTER_SCALE, timeScale);
               break;
            case FASTEST_FLAG:
               timeScale = Math.min(FASTEST_SCALE, timeScale);
               break;
         }
      }
   }

   public static void main(String [] args)
   {
      parseCommandLine(args);
      PApplet.main(VirtualWorld.class);
   }
}
