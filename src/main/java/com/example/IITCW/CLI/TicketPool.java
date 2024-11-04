package com.example.IITCW.CLI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/*By synchronizing the methods and making synchromized lists, it ensures safe concurrent access
   we used synchronized methods to prevent multiple threads from addings tickets at the same time
   same goes for the remove method. Only one thread at a time can access the ticketLists, this prevents
   race conditions and keeps the data consistent, even when multiple threads are working concurrently.
 */

public class TicketPool {
     private final List<String> tickets; //Thread-safe list to hold tickets
     private final int maxCapacity;

     public TicketPool(int maxCapacity) {
         this.tickets = Collections.synchronizedList(new ArrayList<>());//Use Collections.synchronizedList to make it thrad-safe
         this.maxCapacity = maxCapacity;
     }

     //Method to add tickets(used by vendors)
    public boolean addTickets(int numberOfTicketsToAdd) {
        synchronized (tickets) {
            int availableTicketSpace = maxCapacity - tickets.size();
            int actualTicketsAdded = Math.min(numberOfTicketsToAdd, availableTicketSpace);

            if (actualTicketsAdded > 0) {
                for(int i = 0; i < actualTicketsAdded; i++){
                    tickets.add("Ticket " + tickets.size() + 1);
                }
                System.out.println(actualTicketsAdded + " tickets.added. Total tickets: " + tickets.size());
                return true; //The tickets will be successfully added and it will return from the method
            }
            else{
                System.out.println("The maximum capacity has been reached. No more tickets can be added");
                return false;
            }
        }
    }
    //Method to remove a ticket (used by customers)
    public int removeTicket(int numberOfTicketsToRemove){
         synchronized (tickets) {/*This synchronizes the list to prevent multiple threads from removing at the same time*/
             int actualTicketsRemoved = Math.min(numberOfTicketsToRemove, tickets.size());

             if(actualTicketsRemoved > 0){
                 for(int i = 0; i < actualTicketsRemoved; i++)
                 {
                     System.out.println();
                 }
             }


         }
    }
}
