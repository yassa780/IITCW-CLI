package com.example.IITCW.CLI;

public class Vendor implements Runnable {
    private String vendorId;
    private int ticketsPerRelease;//No. of tickets the vendor releases
    private int releaseInterval;
    private TicketPool ticketPool;

    public Vendor(String vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {//This is a built in method where the thread will start executing

        while (true){
            if(ticketPool.isMaxCapacity()){
                System.out.println("Vendor " + vendorId + ":The ticketpool has reached its maximum capacity. Stopping ticket release");
                break;
            }

            System.out.println("Vendor " + vendorId + " is attempting to release tickets");

            boolean ticketsAdded = ticketPool.addTickets(ticketsPerRelease);

            try{
                Thread.sleep(releaseInterval); //Wait for the specified interval before releasing
            }
            catch (InterruptedException e) {
                System.out.println("Vendor ID: " + vendorId + " was interrupted");
                Thread.currentThread().interrupt(); //This will restore the interrupted status
            }

        }
        System.out.println("Vendor ID: " + vendorId + " has finished releasing the tickets");
    }
}
