package com.example.IITCW.CLI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*By synchronizing the methods and making synchromized lists, it ensures safe concurrent access
   we used synchronized methods to prevent multiple threads from addings tickets at the same time
   same goes for the remove method. Only one thread at a time can access the ticketLists, this prevents
   race conditions and keeps the data consistent, even when multiple threads are working concurrently.
 */

public class TicketPool {
    private final List<String> tickets; //Synchronized list to hold tickets
    private final int maxCapacity;
    private boolean sellingComplete = false;
    private final ReentrantLock lock = new ReentrantLock(true); // Fair lock
    private final Condition notEmpty = lock.newCondition(); // Condition for non-empty pool
    private final Condition notFull = lock.newCondition(); // Condition for non-full pool

    public TicketPool(int maxCapacity) {
        this.tickets = Collections.synchronizedList(new ArrayList<>()); //A synchronized list
        this.maxCapacity = maxCapacity;
    }

    //Method to add tickets (used by Vendors)
    public void addTickets(int numberOfTicketsToAdd) {
        lock.lock();
        try{
            while (tickets.size() >= maxCapacity){
                System.out.println("Ticketpool is full. Vendor is waiting.");
                notFull.await(); //Wait until customers consume tickets
            }

            int ticketsToAdd = Math.min(numberOfTicketsToAdd, maxCapacity - tickets.size());
            for(int i = 0; i < ticketsToAdd; i++) {
                tickets.add("Ticket" + (tickets.size() + 1));
            }

            System.out.println(ticketsToAdd + " tickets added. Total tickets: " + tickets.size());
            notEmpty.signalAll(); //Notify customers that tickets are avaialble
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }finally {
            lock.unlock();
        }
        }

    //Method to remove tickets (used by customers)
    public int removeTickets(int numberOfTicketsToRemove) {
        lock.lock();
        try {
            while (tickets.isEmpty() && !sellingComplete) {
                System.out.println("No tickets available. Customer is waiting.");
                notEmpty.await(); // Wait until tickets are added
            }

            if (tickets.isEmpty() && sellingComplete) {
                return 0; // Graceful exit if no tickets are available and selling is complete
            }

            int ticketsToRemove = Math.min(numberOfTicketsToRemove, tickets.size());
            for (int i = 0; i < ticketsToRemove; i++) {
                tickets.remove(0);
            }
            System.out.println(ticketsToRemove + " tickets removed. " + tickets.size() + " tickets remaining.");
            notFull.signalAll(); // Notify waiting vendors
            return ticketsToRemove;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1; // Exit gracefully on interruption
        } finally {
            lock.unlock();
        }
    }

    //Mark ticket selling as complete
    public void setSellingComplete(boolean complete) {
       lock.lock();
       try{
           this.sellingComplete = complete;
           notEmpty.signalAll(); //Notify waiting customers
       }
       finally{
           lock.unlock();
       }
    }

    public int getTicketCount () {
        lock.lock();
        try{
            return tickets.size();
        }
        finally {
            lock.unlock();
        }

    }
    //Check if ticket selling is complete
    public boolean isSellingComplete() {
        lock.lock();
        try{
            return sellingComplete;
        }
        finally {
            lock.unlock();
        }
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}

