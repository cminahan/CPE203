import java.util.*;

/*
EventScheduler: ideally our way of controlling what happens in our virtual world
 */

final class EventScheduler
{
   private final PriorityQueue<Event> eventQueue;
   private final Map<Entity, List<Event>> pendingEvents;
   private final double timeScale;

   public EventScheduler(double timeScale)
   {
      this.eventQueue = new PriorityQueue<>(new EventComparator());
      this.pendingEvents = new HashMap<>();
      this.timeScale = timeScale;
   }

   //getter methods
   public Map<Entity, List<Event>> getPendingEvents(){return this.pendingEvents;}

   //Function methods
   public void scheduleActions(Entity entity, WorldModel world, ImageStore imageStore)
   {
      switch (entity.getKind())
      {
         case OCTO_FULL:
            this.scheduleEvent(entity,
                    entity.createActivityAction(world, imageStore), entity.getActionPeriod());
            this.scheduleEvent(entity, entity.createAnimationAction(0),
                    entity.getAnimationPeriod());
            break;

         case OCTO_NOT_FULL:
            this.scheduleEvent(entity, entity.createActivityAction(world, imageStore),
                    entity.getActionPeriod());
            this.scheduleEvent(entity,
                    entity.createAnimationAction(0), entity.getAnimationPeriod());
            break;

         case FISH:
            this.scheduleEvent(entity,
                    entity.createActivityAction(world, imageStore), entity.getActionPeriod());
            break;

         case CRAB:
            this.scheduleEvent(entity,
                    entity.createActivityAction(world, imageStore), entity.getActionPeriod());
            this.scheduleEvent(entity,
                    entity.createAnimationAction(0), entity.getAnimationPeriod());
            break;

         case QUAKE:
            this.scheduleEvent(entity,
                    entity.createActivityAction(world, imageStore), entity.getActionPeriod());
            this.scheduleEvent(entity,
                    entity.createAnimationAction(Functions.QUAKE_ANIMATION_REPEAT_COUNT),
                    entity.getAnimationPeriod());
            break;

         case SGRASS:
            this.scheduleEvent(entity,
                    entity.createActivityAction(world, imageStore),
                    entity.getActionPeriod());
            break;
         case ATLANTIS:
            this.scheduleEvent(entity,
                    entity.createAnimationAction(Functions.ATLANTIS_ANIMATION_REPEAT_COUNT),
                    entity.getAnimationPeriod());
            break;

         default:
      }
   }

   public void scheduleEvent(Entity entity, Action action, long afterPeriod)
   {
      long time = System.currentTimeMillis() +
              (long)(afterPeriod * this.timeScale);
      Event event = new Event(action, time, entity);

      this.eventQueue.add(event);

      // update list of pending events for the given entity
      List<Event> pending = this.pendingEvents.getOrDefault(entity,
              new LinkedList<>());
      pending.add(event);
      this.pendingEvents.put(entity, pending);
   }

   public void unscheduleAllEvents(Entity entity)
   {
      List<Event> pending = this.pendingEvents.remove(entity);

      if (pending != null)
      {
         for (Event event : pending)
         {
            this.eventQueue.remove(event);
         }
      }
   }

   public void updateOnTime(long time)
   {
      while (!this.eventQueue.isEmpty() &&
              this.eventQueue.peek().getTime() < time)
      {
         Event next = this.eventQueue.poll();

         next.removePendingEvent(this);

         next.getAction().executeAction(this);
      }
   }








}
