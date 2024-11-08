package com.example.IITCW.CLI;

import java.util.ArrayList;
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
         this.tickets = Collections.synchronizedList(new ArrayList<>());//Use Collections.synchronizedList to make it thread-safe
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
                 for(int i = 0; i < actualTicketsRemoved; i++){
                     tickets.remove(0);
                 }
                 System.out.println(actualTicketsRemoved + " tickets removed. " + tickets.size() + "tickets remaining");
                 return tickets.size(); //It returns the remaining ticket count
             }
             else{
                 System.out.println("There are no available tickets to remove");
                 return -1; //This indicated no tickets were available to remove
             }
         }
    }

    //An additional method to check if the pool has reached the maximum capacity
    public boolean isMaxCapacity() {
         synchronized (tickets){
             return tickets.size() >= maxCapacity;
         }
    }
    public int getTicketCount(){
         synchronized (tickets){
             return tickets.size();
         }
    }
}
