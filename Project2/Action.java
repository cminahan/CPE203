/*
Action: ideally what our various entities might do in our virutal world
*/

//import java.util.LinkedList;
//import java.util.List;

public interface Action
{
   Entity getEntity();
   void executeAction(EventScheduler scheduler);
}

