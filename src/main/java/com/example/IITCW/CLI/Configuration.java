package com.example.IITCW.CLI;

import java.io.Serializable;
import java.util.Scanner;

public class Configuration implements Serializable {
    public int totalTickets;
    public int ticketReleaseRate;
    public int customerRetrievalRate;
    public int maxTicketCapacity;

    public Configuration() {}

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;

    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public static Configuration configureSystem(Scanner input, ConfigurationManager configurationManager ){
        System.out.print("Enter the total no. of tickets: ");
        int totalTickets = CLIApplication.inputValidation(input);

        System.out.print("Enter ticket release rate: ");
        int ticketReleaseRate = CLIApplication.inputValidation(input);

        System.out.print("Enter customer retrieval rate: ");
        int customerRetrievalRate = CLIApplication.inputValidation(input);

        System.out.print("Enter the maximum ticket Capacity: ");
        int maximunTicketCapacity = CLIApplication.inputValidation(input);

        //Output the results
        System.out.println();
        System.out.println("Configuration created successfully: ");
        System.out.println("Total tickets: " + totalTickets);
        System.out.println("Ticket release rate: " + ticketReleaseRate);
        System.out.println("Customer retrieval rate: " + customerRetrievalRate);
        System.out.println("Maximum ticket capacity: " + maximunTicketCapacity);

        Configuration config = new Configuration(totalTickets,ticketReleaseRate,customerRetrievalRate,maximunTicketCapacity);
        configurationManager.writeJson(config);

        return config;
    }


    /*@Override
    public String toString() {
        return "Configu"
    }*/
}
