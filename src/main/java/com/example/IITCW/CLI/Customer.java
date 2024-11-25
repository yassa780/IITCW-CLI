package com.example.IITCW.CLI;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final int retrievalInterval;
    private final int customerId;

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int retrievalInterval, int customerId) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.retrievalInterval = retrievalInterval;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (ticketPool) {
                    // Exit if the pool is empty and no more tickets will be added
                    if (ticketPool.isEmpty() && ticketPool.isSellingComplete()) {
                        System.out.println("Customer " + customerId + ": No more tickets available. Stopping.");
                        break; // Stop the thread gracefully
                    }

                    // Wait if the pool is empty
                    while (ticketPool.isEmpty() && !ticketPool.isSellingComplete()) {
                        System.out.println("Customer " + customerId + ": Pool is empty. Waiting for tickets.");
                        ticketPool.wait(); // Wait for tickets to become available
                    }

                    // Attempt to remove tickets if available
                    int ticketsRemoved = ticketPool.removeTickets(customerRetrievalRate);
                    if (ticketsRemoved > 0) {
                        System.out.println("Customer " + customerId + " purchased " + ticketsRemoved + " tickets.");
                    }
                }
                Thread.sleep(retrievalInterval); // Simulate time taken for the customer
            }
        } catch (InterruptedException e) {
            System.out.println("Customer " + customerId + " interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}
