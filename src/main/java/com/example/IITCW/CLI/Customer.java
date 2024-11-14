package com.example.IITCW.CLI;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final int customerId;

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int customerId) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        try{
            while (true){
                if(ticketPool.isEmpty()){
                    System.out.println("Customer " + customerId + ": Pool is empty. Cant purchase anymore tickets");
                    break;//Stop the thread
                }
                int ticketsRemoved = ticketPool.removeTicket(customerRetrievalRate);
                if(ticketsRemoved > 0){
                    System.out.println("Customer " + customerId + " purchased " + ticketsRemoved + " tickets");
                }
                else{
                    System.out.println("Customer " + customerId + " No tickets available to purchase");
                }
                Thread.sleep(1000);
            }
        }
        catch(InterruptedException e){
            System.out.println("Customer " + customerId + " interrupted");
            Thread.currentThread().interrupt();
        }
    }
}
