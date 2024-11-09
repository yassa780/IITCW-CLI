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

    public volatile boolean isRunning = true;

    public void stop(){
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {

            //Checking if there are tickets in the pool
            if (ticketPool.getTicketCount() == 0){
                System.out.println("Customer " + customerId + "could not purchase tickets");
                stop();
            }
            //Attempting ot purchase tickets
            int ticketsRemoved = ticketPool.removeTicket(ticketsToBuy);

            if(ticketsRemoved > 0){
                //Tickets successfully purchased
                totalTicketsPurchased += ticketsRemoved;
                System.out.println("Customer " + customerId + " purchased " + ticketsRemoved + " tickets.");
            }
            else{
                System.out.println("Customer " + customerId + "could not purchase tickets");
                break;
            }
            try{
                Thread.sleep(retrievalInterval); //Wait before attempting the next purchase
            }
            catch (InterruptedException e){
                System.out.println("Customer " + customerId + " was interrupted.");
                Thread.currentThread().interrupt();//restore the interuppted status
                break;
            }
        }
        System.out.println("Customer " + customerId + " has purchased tickets. Total tickets purchased: " + totalTicketsPurchased);
    }

    public int getTotalTicketsPurchased() {
        return totalTicketsPurchased;
    }
}
