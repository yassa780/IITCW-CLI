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
                Logger.info("Vendor" + vendorId + " released " + ticketReleaseRate);

                Thread.sleep(releaseInterval);//Stimulate ticket release rate

                if (ticketPool.getTicketCount() >= ticketPool.getMaxCapacity()) {
                    Logger.logError("Vendor " + vendorId + ": Ticket pool is full.Stopping ticket release.");
                    break;
                }
            }
        } catch (InterruptedException e) {
            Logger.logError("Vendor " + vendorId + " interrupted.");
            Thread.currentThread().interrupt(); // Reset interrupt flag
        }
    }
}
