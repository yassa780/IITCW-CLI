package com.example.IITCW.CLI;

/**
 * Vendor class represents a vendor thread that adds tickets to the ticketpool.
 * It implements runnable interface to allow concurrent execuction
 */
public class Vendor implements Runnable {

    private final TicketPool ticketPool; //Reference to the shared ticketpool
    private final int ticketReleaseRate;
    private final int vendorId;
    private final int releaseInterval;

    /**
     *
     * @param ticketPool
     * @param ticketReleaseRate
     * @param releaseInterval
     * @param vendorId
     */
    public Vendor(TicketPool ticketPool, int ticketReleaseRate, int releaseInterval, int vendorId) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.releaseInterval = releaseInterval;
        this.vendorId = vendorId;
    }

    /**
     * The run method contains the logic executed by the vendor thread.
     * Vendors continuously release tickets into the Ticketpool until the selling process is complete
     */

    @Override
    public void run() {
        try {
            //Loop until ticket selling is marked a complete
            while (true) {
                // Check if ticket selling si complete
                if (ticketPool.isSellingComplete()) {
                    Logger.info("Vendor " + vendorId + ": All tickets sold. Stopping ticket Release.");
                    break; // Exit loop when selling is complete
                }

                //Add tickets to the ticketpool
                ticketPool.addTickets(ticketReleaseRate);
                Logger.info("Vendor " + vendorId + " released " + ticketReleaseRate + " tickets");

                //Stimulate a delay between ticket releases
                Thread.sleep(releaseInterval);//Stimulate ticket release rate

            }
        } catch (InterruptedException e) {
            //Handle thread interruption gracefully and log the event
            Logger.logError("Vendor " + vendorId + " interrupted.");
            Thread.currentThread().interrupt(); // Reset the thread's interrupt flag
        }
    }
}