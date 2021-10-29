import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Position;
import javax.swing.text.View;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LogAnalyzer
{
      //constants to be used when pulling data out of input
      //leave these here and refer to them to pull out values
   private static final String START_TAG = "START";
   private static final int START_NUM_FIELDS = 3;
   private static final int START_SESSION_ID = 1;
   private static final int START_CUSTOMER_ID = 2;
   private static final String BUY_TAG = "BUY";
   private static final int BUY_NUM_FIELDS = 5;
   private static final int BUY_SESSION_ID = 1;
   private static final int BUY_PRODUCT_ID = 2;
   private static final int BUY_PRICE = 3;
   private static final int BUY_QUANTITY = 4;
   private static final String VIEW_TAG = "VIEW";
   private static final int VIEW_NUM_FIELDS = 4;
   private static final int VIEW_SESSION_ID = 1;
   private static final int VIEW_PRODUCT_ID = 2;
   private static final int VIEW_PRICE = 3;
   private static final String END_TAG = "END";
   private static final int END_NUM_FIELDS = 2;
   private static final int END_SESSION_ID = 1;

      //a good example of what you will need to do next
      //creates a map of sessions to customer ids
   private static void processStartEntry(
      final String[] words,
      final Map<String, List<String>> sessionsFromCustomer)
   {
      if (words.length != START_NUM_FIELDS)
      {
         return;
      }

         //check if there already is a list entry in the map
         //for this customer, if not create one
      List<String> sessions = sessionsFromCustomer.get(words[START_CUSTOMER_ID]);
      if (sessions == null)
      {
         sessions = new LinkedList<>();
         sessionsFromCustomer.put(words[START_CUSTOMER_ID], sessions);
      }

         //now that we know there is a list, add the current session
      sessions.add(words[START_SESSION_ID]);
   }

      //similar to processStartEntry, should store relevant view
      //data in a map - model on processStartEntry, but store
      //your data to represent a view in the map (not a list of strings)
   private static void processViewEntry(final String[] words, final Map<String, List<Views>> viewsFromSession)
   {
      if(words.length != VIEW_NUM_FIELDS)
         return;

      //check if there already is a a list entry in the map
      //for this session, if not create one
      List<Views> views = viewsFromSession.computeIfAbsent(words[VIEW_SESSION_ID], k -> new LinkedList<>());

      //now that we know there is a list, add the current view
      Views new_view = new Views(words[VIEW_PRODUCT_ID], Integer.parseInt(words[VIEW_PRICE]));
      views.add(new_view);
   }

      //similar to processStartEntry, should store relevant purchases
      //data in a map - model on processStartEntry, but store
      //your data to represent a purchase in the map (not a list of strings)
   private static void processBuyEntry(final String[] words, Map<String, List<Buy>> buysFromSession)
   {
      if(words.length != BUY_NUM_FIELDS)
         return;

      //check if there already is a list entry in the map
      //for this buy, if not create one
      List<Buy> buys = buysFromSession.computeIfAbsent(words[BUY_SESSION_ID], k -> new LinkedList<>());

      //now that we know there is a list, add the current buy
      Buy new_buy = new Buy(words[BUY_PRODUCT_ID], Integer.parseInt(words[BUY_PRICE]),
              Integer.parseInt(words[BUY_QUANTITY]));
      buys.add(new_buy);
   }

   private static void processEndEntry(final String[] words)
   {
      if (words.length != END_NUM_FIELDS)
      {
         return;
      }
   }

      //this is called by processFile below - its main purpose is
      //to process the data using the methods you write above
   private static void processLine(final String line, final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<Views>> viewsFromSession, final Map<String, List<Buy>> buysFromSession)
   {
      final String[] words = line.split("\\h");

      if (words.length == 0)
      {
         return;
      }

      switch (words[0])
      {
         case START_TAG:
            processStartEntry(words, sessionsFromCustomer);
            break;
         case VIEW_TAG:
            processViewEntry(words, viewsFromSession);
            break;
         case BUY_TAG:
            processBuyEntry(words, buysFromSession);
            break;
         case END_TAG:
            processEndEntry(words);
            break;
      }
   }

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   private static void printSessionPriceDifference(final Map<String, List<Views>> viewFromSession,
                                                   final Map<String, List<Buy>> buysFromSession,
                                                   final Map<String, List<String>> sessionsFromCustomer)
   {
      System.out.println("Price Difference for Purchased Product by Session");

      //set variables to be used
      int price_tot;
      double avg;
      double dif;
      int price;

      //go through the sessions from the customers
      List<String> purchases = new LinkedList<>();
      for(List<String> list1 : sessionsFromCustomer.values()){
         for(String session_id : list1){
            List<Buy> buys = buysFromSession.get(session_id);
            //check if there is nothing in this spot yet
            //then add one to it
            if(buys != null)
               purchases.add(session_id);
         }
      }
      //go through the list
      for(String session : purchases){
         System.out.println(session);
         //go through the list for buys
         for (Buy the_buys : buysFromSession.get(session)){
            price_tot = 0;
            price = the_buys.getPrice();
            String product_id = the_buys.getProudctId();
            //go through the views
            for(Views views : viewFromSession.get(session)){
               price_tot = price_tot + views.getProductPrice();
            }
            //calculate the average and display the difference compared to the price
            avg = ((double)price_tot) / viewFromSession.get(session).size();
            dif = price - avg;
            System.out.println("\t" + product_id + " " + (dif));
         }
      }
   }

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   private static void printCustomerItemViewsForPurchase(final Map<String, List<String>> sessionsFromCustomer,
                                                         final Map<String, List<Views>> viewsFromSession,
                                                         final Map<String, List<Buy>> buysFromSession)
   {
      System.out.println("Number of Views for Purchased Product by Customer");

      //variables to be used
      boolean x;
      int views;

      //loop through list of sessions from customers
      for(String customer : sessionsFromCustomer.keySet()){
         x = true;
         //get sessionID
         List<String> sessions = sessionsFromCustomer.get(customer);
         List<String> listOfProducts = new LinkedList<>();
         for(String session : sessions){
            //the purchases of sessions
            List<Buy> buys = buysFromSession.get(session);
            //check if a purchase exists
            if(buys != null){
               if(x){
                  x = false;
                  System.out.println(customer);
               }
               for(Buy buy : buys){
                  if(listOfProducts.contains(buy) == false)
                     listOfProducts.add(buy.getProudctId());
               }
            }
         }
         //loop through productIDs
         for(String thing : listOfProducts){
            views = 0;
            for(String session : sessionsFromCustomer.get(customer)){
               List<Views> view = viewsFromSession.get(session);
               //check is view exists
               if(view != null){
                  for(Views new_view : viewsFromSession.get(session)){
                     //check if the products match, then increase the number of views
                     if(new_view.getProductId().equals(thing)){
                        views++;
                        break;
                     }

                  }
               }

            }
            System.out.println("\t" + thing + " " + views);
         }
      }
   }

   //calculate the average views without a purchase
   public static void printCustomerAverageViewWithNoPurchase(final Map<String, List<Views>> viewsFromSession,
                                                             final Map<String, List<Buy>> buysFromSession,
                                                             final Map<String, List<String>> sessionsFromCustomers)
   {
      //variables to be used
      int count = 0;
      double avg;

      //print out the statement
      System.out.print("Average Views without Purchase: ");

      List<String> noPurchase = new LinkedList<>();
      for(List<String> sessions : sessionsFromCustomers.values()){
         for(String id : sessions){
            List<Buy> buy = buysFromSession.get(id);
            if(buy == null)
               noPurchase.add(id);
         }
      }
      for(String session : noPurchase){
         List<Views> views = viewsFromSession.get(session);
         if(views != null)
            count+= views.size();
      }
      avg = ((double)count / noPurchase.size());
      System.out.println(avg);
   }


      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   private static void printStatistics(final Map<String, List<Views>> viewsFromSession,
                                       final Map<String, List<Buy>> buysFromSession,
                                       final Map<String, List<String>> sessionsFromCustomer)
   {
      printCustomerAverageViewWithNoPurchase(viewsFromSession, buysFromSession, sessionsFromCustomer);
      printSessionPriceDifference(viewsFromSession, buysFromSession, sessionsFromCustomer);
      printCustomerItemViewsForPurchase(sessionsFromCustomer, viewsFromSession, buysFromSession);

      /* This is commented out as it will not work until you read
         in your data to appropriate data structures, but is included
         to help guide your work - it is an example of printing the
         data once propogated */
      //printOutExample(sessionsFromCustomer, viewsFromSession, buysFromSession);

   }

   /* provided as an example of a method that might traverse your
      collections of data once they are written 
      commented out as the classes do not exist yet - write them! */

   /*private static void printOutExample(
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<Views>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession) 
   {
      //for each customer, get their sessions
      //for each session compute views
      for(Map.Entry<String, List<String>> entry: 
         sessionsFromCustomer.entrySet()) 
      {
         System.out.println(entry.getKey());
         List<String> sessions = entry.getValue();
         for(String sessionID : sessions)
         {
            System.out.println("\tin " + sessionID);
            List<Views> theViews = viewsFromSession.get(sessionID);
            for (Views thisView: theViews)
            {
               System.out.println("\t\tlooked at " + thisView.getProductId());
            }
         }
      }
   }*/


      //called in populateDataStructures
   private static void processFile(final Scanner input, final Map<String, List<String>> sessionsFromCustomer,
                                   final Map<String, List<Views>> viewsFromSession,
                                   final Map<String, List<Buy>> buysFromSession)
   {
      while (input.hasNextLine())
      {
         processLine(input.nextLine(), sessionsFromCustomer, viewsFromSession, buysFromSession);
      }
   }

      //called from main - mostly just pass through important data structures
   private static void populateDataStructures(final String filename,
                                              final Map<String, List<String>> sessionsFromCustomer,
                                              final Map<String, List<Views>> viewsFromSession,
                                              final Map<String, List<Buy>> buysFromSession)
      throws FileNotFoundException
   {
      try (Scanner input = new Scanner(new File(filename)))
      {
         processFile(input, sessionsFromCustomer, viewsFromSession, buysFromSession);
      }
   }

   private static String getFilename(String[] args)
   {
      if (args.length < 1)
      {
         System.err.println("Log file not specified.");
         System.exit(1);
      }

      return args[0];
   }

   public static void main(String[] args)
   {
      /* Map from a customer id to a list of session ids associated with
       * that customer.
       */
      final Map<String, List<String>> sessionsFromCustomer = new HashMap<>();

      /* create additional data structures to hold relevant information */
      /* they will most likely be maps to important data in the logs */
      final Map<String, List<Views>> viewsFromSession = new HashMap<>();
      final Map<String, List<Buy>> buysFromSession = new HashMap<>();

      final String filename = getFilename(args);

      try
      {
         populateDataStructures(filename, sessionsFromCustomer, viewsFromSession, buysFromSession);
         printStatistics(viewsFromSession, buysFromSession, sessionsFromCustomer);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }
}
