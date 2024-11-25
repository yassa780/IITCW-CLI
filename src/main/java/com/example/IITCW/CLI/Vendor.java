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
                synchronized (ticketPool) {
                    if (ticketPool.isFull()) {
                        System.out.println("Vendor " + vendorId + ": Pool is full. Stopping ticket release.");
                        ticketPool.setSellingComplete(true); // Mark selling as complete
                        break; // Stop the thread
                    }

                    ticketPool.addTickets(ticketReleaseRate);
                    System.out.println("Vendor " + vendorId + " released " + ticketReleaseRate + " tickets.");
                }

                Thread.sleep(releaseInterval); // Release tickets at specified intervals
            }
        } catch (InterruptedException e) {
            System.out.println("Vendor " + vendorId + " interrupted.");
            Thread.currentThread().interrupt(); // Reset interrupt flag
        }
    }
}
