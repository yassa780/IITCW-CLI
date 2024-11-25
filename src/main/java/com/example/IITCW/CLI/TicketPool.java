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
    private final List<String> tickets; //Synchronized list to hold tickets
    private final int maxCapacity;
    private boolean sellingComplete = false;

    public TicketPool(int maxCapacity) {
        this.tickets = Collections.synchronizedList(new ArrayList<>()); //A synchronized list
        this.maxCapacity = maxCapacity;
    }

    //Method to add tickets (used by Vendors)
    public void addTickets(int numberOfTicketsToAdd) {
        synchronized (tickets) { //Synchronized the lists for compound actions
            while (tickets.size() >= maxCapacity){
                try{
                    System.out.println("Ticketpool is full. Vendor is waiting.");
                    tickets.wait(); //Wait until customers consume tickets
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            int ticketsToAdd = Math.min(numberOfTicketsToAdd, maxCapacity - tickets.size());
            for(int i = 0; i < ticketsToAdd; i++) {
                tickets.add("Ticket" + (tickets.size() + 1));
            }

            System.out.println(ticketsToAdd + " tickets added. Total tickets: " + tickets.size());
            tickets.notifyAll(); //Notify customers that tickets are avaialble
        }
    }

    //Method to remove tickets (used by customers)
    public int removeTickets(int numberOfTicketsToRemove) {
        synchronized (tickets) {
            while (tickets.isEmpty() && !sellingComplete) {
                try{
                    System.out.println("No tickets available. Customer is waiting.");
                    tickets.wait();// Wait for tickets to become avaialble
                }
                catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    return -1; //Exit gracefully on interruption
                }
            }

            if (tickets.isEmpty() && sellingComplete) {
                return 0; //Graceful exit if no tickets are available and selling is complete
            }

            int ticketsToRemove = Math.min(numberOfTicketsToRemove, tickets.size());
            for (int i = 0; i < ticketsToRemove; i++) {
                tickets.remove(0);
            }

            System.out.println(ticketsToRemove + " tickets removed. " + tickets.size() + " tickets remaining.");
            tickets.notifyAll(); //Notify vendors hat space is available
            return ticketsToRemove;
        }
    }

    //Mark ticket selling as complete
    public synchronized void setSellingComplete(boolean complete) {
        sellingComplete = complete;
        synchronized (tickets) {
            tickets.notifyAll();// Notify all waiting threads to check conditions
        }
    }

    public int getTicketCount () {
        return tickets.size();
    }

    //Check if the pool is full
    public boolean isFull() {
        return tickets.size() >= maxCapacity;
    }

    public boolean isEmpty (){
        return tickets.isEmpty();
    }

    //Check if ticket selling is complete
    public synchronized boolean isSellingComplete() {
        return sellingComplete;
    }

}

