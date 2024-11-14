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
     private boolean isFull = false;
     private boolean isEmpty = true;

     public TicketPool(int maxCapacity) {
         this.tickets = Collections.synchronizedList(new ArrayList<>());//Use Collections.synchronizedList to make it thread-safe
         this.maxCapacity = maxCapacity;
     }

     //Method to add tickets(used by vendors)
    public synchronized boolean addTickets(int numberOfTicketsToAdd) {
        if (tickets.size() >= maxCapacity) {
            isFull = true;//Set the flag if the pool is full
            System.out.println("Ticket pool has reached its maximum capacity");
            return false;
        }

        int ticketsToAdd = Math.min(numberOfTicketsToAdd, maxCapacity - tickets.size());

        for (int i = 0; i < ticketsToAdd; i++) {
            tickets.add("Ticket " + (tickets.size() + 1));
        }

        Logger.info(ticketsToAdd + " tickets added. Total tickets: " + tickets.size());

        isEmpty = false; //Reset the "empty" flag since tickets were added
        notifyAll();//Will notify the customers waiting for tickets
        return true;
    }
    //Method to remove a ticket (used by customers)
    public synchronized int removeTicket(int numberOfTicketsToRemove){
        while(tickets.isEmpty()){
            try{
                isEmpty = true; //Set the flag if the poool is Empty
                System.out.println("No tickets available. Waiting");
                notifyAll();
                wait(); //Waits until tickets are avaialble
            } catch (InterruptedException e) {
                Logger.logError("Thread interrupted while waiting for tickets");
                Thread.currentThread().interrupt();
                return -1;
            }
        }

        /* This ensures that the number of tickets requested to remove does not exceed the current no. of tickets*/
        int ticketsToRemove = Math.min(numberOfTicketsToRemove, tickets.size());
        for (int i = 0; i < ticketsToRemove; i++){
            tickets.remove(0);
        }

        isFull = false; //Reset the "full" flag since tickets were removed
        notifyAll();//Notify the waiting thread

        System.out.println(ticketsToRemove + " tickets removed. " + tickets.size() + " tickets remaining.");

        return ticketsToRemove;
    }

    //An additional method to check if the pool has reached the maximum capacity
   /*public boolean isMaxCapacity() {
         synchronized (tickets){
             return tickets.size() >= maxCapacity;
         }
    }
*/
    public synchronized int getTicketCount(){
        return tickets.size();
    }

    public boolean isFull() {
        return isFull;
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
