package com.example.IITCW.CLI;

import java.io.Serializable;

public class Configuration implements Serializable {
    public int totalTickets;
    public int ticketReleaseDate;
    public int customerRetrievalDate;
    public int maxTicketCapacity;

    public Configuration() {}

    public Configuration(int totalTickets, int ticketReleaseDate, int customerRetrievalDate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseDate = ticketReleaseDate;
        this.customerRetrievalDate = customerRetrievalDate;
        this.maxTicketCapacity = maxTicketCapacity;

    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseDate() {
        return ticketReleaseDate;
    }

    public void setTicketReleaseDate(int ticketReleaseDate) {
        this.ticketReleaseDate = ticketReleaseDate;
    }

    public int getCustomerRetrievalDate() {
        return customerRetrievalDate;
    }

    public void setCustomerRetrievalDate(int customerRetrievalDate) {
        this.customerRetrievalDate = customerRetrievalDate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }


    /*@Override
    public String toString() {
        return "Configu"
    }*/
}
