import java.util.*;

/*
EventScheduler: ideally our way of controlling what happens in our virtual world
 */

final class EventScheduler
{
   private PriorityQueue<Event> eventQueue;
   private Map<Entity, List<Event>> pendingEvents;
   private double timeScale;


   public EventScheduler(double timeScale)
   {
      this.eventQueue = new PriorityQueue<>(new EventComparator());
      this.pendingEvents = new HashMap<>();
      this.timeScale = timeScale;
   }
   /*//25
   public void scheduleActions(Entity entity, WorldModel world, ImageStore imageStore)
   {
      switch (entity.getKind())
      {
         case OCTO_FULL:
            scheduleEvent(entity, entity.createActivityAction(world, imageStore), entity.getActionPeriod());
            scheduleEvent(entity, entity.createAnimationAction(0), entity.getAnimationPeriod());
            break;

         case OCTO_NOT_FULL:
            scheduleEvent(entity, entity.createActivityAction(world, imageStore),
                    entity.getActionPeriod());
            scheduleEvent(entity, entity.createAnimationAction(0), entity.getAnimationPeriod());
            break;

         case FISH:
            scheduleEvent(entity, entity.createActivityAction(world, imageStore), entity.getActionPeriod());
            break;

         case CRAB:
            scheduleEvent(entity, entity.createActivityAction(world, imageStore), entity.getActionPeriod());
            scheduleEvent(entity, entity.createAnimationAction(0), entity.getAnimationPeriod());
            break;

         case QUAKE:
            scheduleEvent(entity, entity.createActivityAction(world, imageStore), entity.getActionPeriod());
            scheduleEvent(entity, entity.createAnimationAction(QUAKE_ANIMATION_REPEAT_COUNT), entity.getAnimationPeriod());
            break;

         case SGRASS:
            scheduleEvent(entity, entity.createActivityAction(world, imageStore), entity.getActionPeriod());
            break;
         case ATLANTIS:
            scheduleEvent(entity, entity.createAnimationAction(ATLANTIS_ANIMATION_REPEAT_COUNT), entity.getAnimationPeriod());
            break;

         default:
      }
   }*/
   //26
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
   //38
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
   //39
   private void removePendingEvent(Event event)
   {
      List<Event> pending = this.pendingEvents.get(event.getEntity());

      if (pending != null)
      {
         pending.remove(event);
      }
   }
   //43
   public void updateOnTime(long time)
   {
      while (!this.eventQueue.isEmpty() &&
              this.eventQueue.peek().getTime() < time)
      {
         Event next = this.eventQueue.poll();

         this.removePendingEvent(next);

         next.getAction().executeAction(this);
      }
   }

}
