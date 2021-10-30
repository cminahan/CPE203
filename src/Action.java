/*
Action: ideally what our various entities might do in our virutal world
 */

public abstract class Action
{
   public Action(){}
   protected abstract void executeAction(EventScheduler scheduler);

}
