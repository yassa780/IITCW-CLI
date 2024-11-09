package com.example.IITCW.CLI;

public class Vendor implements Runnable {
    private String vendorId;
    private int ticketsPerRelease;//No. of tickets the vendor releases
    private int releaseInterval;//The time interval that the vendor waits between each ticket release
    private TicketPool ticketPool; //Created an instance of Ticketpool to access methods from its class
    private int totalTicketsReleased = 0; //Counter to track how many tickets were released by each vendor

    public Vendor(String vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    private volatile boolean isRunning = true;
    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {//This is a built in method where the thread will start executing

        while (isRunning){
            if(ticketPool.isMaxCapacity()){
                System.out.println("Vendor ID: " + vendorId + ":The ticketpool has reached its maximum capacity. Stopping ticket release");
                break;
            }

            System.out.println("Vendor ID: " + vendorId + " is attempting to release tickets");
            boolean ticketsAdded = ticketPool.addTickets(ticketsPerRelease);

            if(ticketsAdded){
                totalTicketsReleased += ticketsPerRelease;
            }

            try{
                Thread.sleep(releaseInterval); //Wait for the specified interval before releasing
            }
            catch (InterruptedException e) {
                System.out.println("Vendor ID: " + vendorId + " was interrupted");
                Thread.currentThread().interrupt(); /*This will restore the interrupted status and it lets other part of the
                program know that the thread was interrupted*/
            }

        }
        System.out.println("Vendor ID: " + vendorId + " has finished releasing the tickets");
    }

    public int getTotalTicketsReleased() {
        return totalTicketsReleased;
    }
}
