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
        try{
            while (true){
                if(ticketPool.isEmpty()){
                  Logger.info("Customer " + customerId + ": Pool is empty. Cant purchase anymore tickets");
                    break;//Stop the thread
                }
                int ticketsRemoved = ticketPool.removeTicket(customerRetrievalRate);
                if(ticketsRemoved > 0){
                    Logger.info("Customer " + customerId + " purchased " + ticketsRemoved + " tickets");
                }
                else{
                    Logger.info("Customer " + customerId + " No tickets available to purchase");
                }
                Thread.sleep(retrievalInterval);
            }
        }
        catch(InterruptedException e){
            System.out.println("Customer " + customerId + " interrupted");
            Thread.currentThread().interrupt();
        }
    }
}
