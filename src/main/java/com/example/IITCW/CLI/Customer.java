package com.example.IITCW.CLI;

public class Customer implements Runnable {
    private String customerId;
    private int retrievalInterval;
    private int ticketsToBuy;
    private TicketPool ticketPool;
    private int totalTicketsPurchased = 0;

    public Customer(String customerId, int retrievalInterval, int ticketsToBuy,TicketPool ticketPool) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketsToBuy = ticketsToBuy;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (true) {

            //Attempt to remove a ticket from the ticket pool
            int remainingTickets = ticketPool.removeTicket(ticketsToBuy);

            if(remainingTickets >= 0){
                System.out.println("Customer " + customerId + "purchased " + ticketsToBuy );
            }
            else{
                System.out.println("Customer " + customerId + " attempted to purchase tickets but none were available");
                break;
            }

            try{
                Thread.sleep(retrievalInterval); //Wait before attempting the next purchase
            }
            catch(InterruptedException e){
                System.out.println("Customer " + customerId + " was interupted");
                Thread.currentThread().interrupt();  /*This will restore the interrupted status and it lets other part of the
                program know that the thread was interrupted*/
                break;
            }
        }
        System.out.println("Customer " + customerId + " has finished attempting to purchase tickets");
    }

    public int getTotalTicketsPurchased() {
        return totalTicketsPurchased;
    }
}
