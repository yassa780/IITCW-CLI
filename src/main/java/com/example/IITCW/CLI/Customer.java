package com.example.IITCW.CLI;

/**
 * Customer class represents a customer thread that attempts to purchase tickets from the ticketpool.
 * It implements the Runnable interface to allow concurrent execution
 */
public class Customer implements Runnable {
    private final TicketPool ticketPool; //Reference to the shared ticketpool
    private final int customerRetrievalRate; //number of tickets a customer attempts to retrieve
    private final int retrievalInterval; //Time interval (in milliseconds) between each ticket
    private final int customerId; //Unique indentifier for the customer

    /**
     *
     * @param ticketPool
     * @param customerRetrievalRate
     * @param retrievalInterval
     * @param customerId
     */
    public Customer(TicketPool ticketPool, int customerRetrievalRate, int retrievalInterval, int customerId) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.retrievalInterval = retrievalInterval;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        try {
            //Loops until the ticket selling is marked as complete and no tickets remain
            while (true) {
                //Checks whether ticket selling is complete and no tickets are left
                if (ticketPool.isSellingComplete() && ticketPool.getTicketCount() == 0) {
                    Logger.info("Customer " + customerId + ": No tickets available to purchase.");
                    break; // Exit loop when selling is complete
                }
                //Attempts to retrieve tickets from the Ticketpool
                int ticketsRemoved = ticketPool.removeTickets(customerRetrievalRate);

                if (ticketsRemoved > 0) {
                    Logger.info("Customer " + customerId + " purchased " + ticketsRemoved + " tickets");
                }
                Thread.sleep(retrievalInterval); //Stimulate a delay between actions
            }
        }
        catch (InterruptedException e) {
            Logger.logError("Customer " + customerId + " interrupted."); //Handles the thread exception
            Thread.currentThread().interrupt(); //Reset the thread's interrupt flagr
        }
    }
}