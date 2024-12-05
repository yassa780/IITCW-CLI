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

                int ticketsRemoved = ticketPool.removeTickets(customerRetrievalRate);

                if (ticketsRemoved > 0) {
                    Logger.info("Customer " + customerId + " purchased " + ticketsRemoved + " tickets");
                }else if (ticketsRemoved == 0 && ticketPool.isSellingComplete()) {
                    Logger.logError("Customer " + customerId + ": No more tickets available. Stopping");
                    break; // Exit the loop gracefully
                }

                Thread.sleep(retrievalInterval); //Stimulate a delay between actions
            }
        }
        catch (InterruptedException e) {
            Logger.logError("Customer " + customerId + " interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}
