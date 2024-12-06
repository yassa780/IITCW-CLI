package com.example.IITCW.CLI;

public class Vendor implements Runnable {

    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final int vendorId;
    private final int releaseInterval;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate, int releaseInterval, int vendorId) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.releaseInterval = releaseInterval; // Set interval
        this.vendorId = vendorId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ticketPool.addTickets(ticketReleaseRate);
                Logger.info("Vendor " + vendorId + " released " + ticketReleaseRate + " tickets");

                Thread.sleep(releaseInterval);//Stimulate ticket release rate

                if (ticketPool.getTicketCount() >= ticketPool.getMaxCapacity() || ticketPool.isSellingComplete()) {
                    Logger.logError("Vendor " + vendorId + ": All tickets have been sold");
                    break;
                }
            }
        } catch (InterruptedException e) {
            Logger.logError("Vendor " + vendorId + " interrupted.");
            Thread.currentThread().interrupt(); // Reset interrupt flag
        }
    }
}