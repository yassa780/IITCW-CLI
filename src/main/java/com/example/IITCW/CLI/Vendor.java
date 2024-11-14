package com.example.IITCW.CLI;

public class Vendor implements Runnable {

    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final int vendorId;
    private final int releaseInterval;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate, int releaseInterval, int vendorId) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.releaseInterval = releaseInterval; //Set interval
        this.vendorId = vendorId;

    }

    @Override
    public void run() {
        try{
            while(true) {

                if(ticketPool.isFull()){
                    Logger.info("Vendor " + vendorId + ": Pool is full. Stopping ticket release");
                    break;//Stop the thread
                }
                if (!ticketPool.addTickets(ticketReleaseRate)){
                    Logger.info("Vendor " + vendorId + ": Maximum ticket capacity reach. Stopping ticket release");
                    break;
                }
                Logger.info("Vendor " + vendorId + " released " + ticketReleaseRate + " tickets.");
                Thread.sleep(releaseInterval);//Release tickets at specified intervals
            }
        }
        catch (InterruptedException e){
            System.out.println("Vendor " + vendorId + " interrupted");
            Thread.currentThread().interrupt();
        }
    }
}
