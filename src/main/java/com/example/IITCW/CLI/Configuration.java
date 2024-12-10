package com.example.IITCW.CLI;

import java.io.Serializable;
import java.util.Scanner;

/**
 * This class represents the configuration settings for the ticketing system.
 */
public class Configuration implements Serializable {
    public int totalTickets; //Total number of tickets to be sold
    public int ticketReleaseRate; //Rate at which tickets are released by vendors
    public int customerRetrievalRate; //Rate st which customers retrieve tickets
    public int maxTicketCapacity; //Maximum capacity of the ticket pool

    /**
     * Default constructor
     * Used when creating an empty configuration instance
     */
    public Configuration() {}

    /**
     *
     * @param totalTickets
     * @param ticketReleaseRate
     * @param customerRetrievalRate
     * @param maxTicketCapacity
     */
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;

    }

    /**
     * Getter and setter methods
     * @return
     */
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

    /**
     * Configures the system by collecting input from the user and validating it
     * The result configuration is written to a JSON file for future use
     * @param input
     * @param configurationManager ConfigurationManager to handle saving the configuration
     * @return The created Configuration object
     */
    public static Configuration configureSystem(Scanner input, ConfigurationManager configurationManager ){

        int maximunTicketCapacity; //Variable to store the maximum ticket pool capacity

        //prompt the user for total tickets sold
        System.out.print("Enter the total no. of tickets to be sold: ");
        int totalTickets = CLIApplication.inputValidation(input);

        //prompt the user for ticket release rate
        System.out.print("Enter ticket release rate: ");
        int ticketReleaseRate = CLIApplication.inputValidation(input);

        //Prompt the user for customer retrieval rate
        System.out.print("Enter customer retrieval rate: ");
        int customerRetrievalRate = CLIApplication.inputValidation(input);

        while (true){
            System.out.print("Enter the maximum ticket Capacity of the Ticket Pool: ");
            maximunTicketCapacity = CLIApplication.inputValidation(input);

            //Validation to ensure maxTicketCapacity >= total Tickets to be sold

            if (maximunTicketCapacity > totalTickets) {
                ConfigurationManager.errorMessage("Total number of tickets to be sold should be greater than the maximum ticket capacity");
                continue;
            }
            break;
        }


        //Output the results
        System.out.println();
        System.out.println("Configuration created successfully: ");
        System.out.println("Total tickets to be sold: " + totalTickets);
        System.out.println("Ticket release rate: " + ticketReleaseRate);
        System.out.println("Customer retrieval rate: " + customerRetrievalRate);
        System.out.println("Maximum ticket capacity of the Ticket Pool: " + maximunTicketCapacity);

        //Create and save the configuration
        Configuration config = new Configuration(totalTickets,ticketReleaseRate,customerRetrievalRate,maximunTicketCapacity);
        configurationManager.writeJson(config);

        return config; //Return the created Configuration object
    }
}
