package com.example.IITCW.CLI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Ticketpool manages a synchronized pool of tickets to ensure safe concurrent access by multiple threads.
 * It uses synchronization mechanisms to prevent race conditions and maintain data consistency
 * */


public class TicketPool {
    private final List<String> tickets; //Synchronized list to hold tickets
    private final int maxCapacity; //Maximum capacity of the ticketpool
    private int totalTicketsRemaining; // Tracks remaining tickets to be sold
    private boolean sellingComplete = false; //Flag to indicate that the selling is complete

    private final ReentrantLock lock = new ReentrantLock(true); // Fair lock for thread-safe operations
    private final Condition notEmpty = lock.newCondition(); // Condition for non-empty pool
    private final Condition notFull = lock.newCondition(); // Condition for non-full pool

    /**
     * Constructor for Ticketpool
     * @param maxCapacity Maximum capacity of the ticketpool
     * @param totalTickets Total number of tickets available for sale
     */
    public TicketPool(int maxCapacity, int totalTickets) {
        this.tickets = Collections.synchronizedList(new ArrayList<>()); //A synchronized thread-safe list
        this.maxCapacity = maxCapacity;
        this.totalTicketsRemaining = totalTickets;
    }

    //Method to add tickets (used by Vendors)
    public void addTickets(int numberOfTicketsToAdd) {
        lock.lock(); //Aquire the lock for thread-safe operation
        try{
            while (tickets.size() == maxCapacity) {
                Logger.info("Ticketpool is full. Vendor is waiting.");
                notFull.await(); //Wait until customers consume tickets
                if (sellingComplete) return; //Exit if selling is marked complete. The program is terminated by this
            }
            if (totalTicketsRemaining == 0) {
                Logger.info("All tickets have been sold. Vendor is stopping.");
                sellingComplete = true; //Mark selling as completed
                notEmpty.signalAll(); // Notify any waiting customers
                notFull.signalAll(); // Notify any waiting vendors
                return;
            }

            //Calculates how many tickets can be added without exceeding maxCapacity
            int ticketsToAdd = Math.min(numberOfTicketsToAdd, maxCapacity - tickets.size());
            for(int i = 0; i < ticketsToAdd; i++) {
                tickets.add("Ticket" + (tickets.size() + 1)); //Add tickets to the pool
            }
            totalTicketsRemaining -= ticketsToAdd; //Update remaining tickets count



            Logger.info(ticketsToAdd + " tickets added. Total tickets: " + tickets.size());
            notEmpty.signalAll(); //Notify customers that tickets are available
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt(); //Handle thread interruption
        }finally {
            lock.unlock(); //Release the lock
        }
    }


    /**
     * Removes tickets from the pool. This method is used by customer threads.
     * @param numberOfTicketsToRemove Number of tickets to remove from the pool
     * @return Number of tickets successfully removed
     */
    public int removeTickets(int numberOfTicketsToRemove) {
        lock.lock(); //Acquire the lock for thread-safe operation
        try {
            while (tickets.isEmpty() && !sellingComplete) {
                Logger.logError("No tickets available. Customer is waiting.");
                notEmpty.await(); // Wait until tickets are added
                if (sellingComplete) return 0; //Exit if selling is marked complete
            }
            if (tickets.isEmpty() && sellingComplete) {
                Logger.logError("Ticketpool is empty. Customer is stopping");
                return 0; // Graceful exit if no tickets are available and selling is complete
            }
            //Calculate how many tickets can be removed without exceeding available tickets
            int ticketsToRemove = Math.min(numberOfTicketsToRemove, tickets.size());
            for (int i = 0; i < ticketsToRemove; i++) {
                tickets.remove(0); //Remove the tickets from the ticketpool
            }
            Logger.logError(ticketsToRemove + " tickets removed. " + tickets.size() + " tickets remaining.");
            notFull.signalAll(); // Notify vendors that space is available
            return ticketsToRemove; //Return the number of tickets removed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); //Handle thread interruption
            return -1; // Exit gracefully on interruption
        } finally {
            lock.unlock(); //Releases the lock
        }
    }


    /**
     * Marks ticket selling as complete and notifies all waiting threads
     * @param complete Boolean indicating whether selling is complete
     */
    public void setSellingComplete(boolean complete) {
        lock.lock(); //Acquire the lock for thread-safe operation
        try{
            this.sellingComplete = complete;
            notEmpty.signalAll(); //Notify waiting customers
            notFull.signalAll(); //Notify waiting vendors
        }
        finally{
            lock.unlock(); //Release the lock
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