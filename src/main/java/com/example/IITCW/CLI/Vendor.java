package com.example.IITCW.CLI;

public class Vendor implements Runnable {
    private int vendorId;
    private TicketPool ticketPool;
    private int ticketsPerRelease;//No. of tickets the vendor releases
    private int releaseInterval;

    public Vendor(TicketPool ticketPool, int ticketsToRelease, int releaseInterval) {
        this.ticketPool = ticketPool;
        this.ticketsPerRelease = ticketsToRelease;
    }

    @Override
    public void run() {//This is a built in method where the thread will start executing
        for(int i = 0; i < ticketsPerRelease; i++){

        }
    }
}
