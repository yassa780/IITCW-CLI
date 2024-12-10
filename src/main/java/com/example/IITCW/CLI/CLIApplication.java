package com.example.IITCW.CLI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.IITCW.CLI.Configuration.configureSystem;

/**
 * This serves as the entry point for the Ticketing System
 * It allows users to configure the system, start the program and manage vendor and customer threads
 */
public class CLIApplication  {

    //ANSI colour code for yellow text (used for the console output)
    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {//This is the main thread
        Scanner input = new Scanner(System.in);
        ConfigurationManager configurationManager = new ConfigurationManager();
        Configuration config = null; //Holds the current system configuration

        //Displays the applicat
        System.out.println( YELLOW +
                "\n" +
                " _____                _   _      \n" +
                "|  ___|              | | (_)     \n" +
                "| |____   _____ _ __ | |_ ___  __\n" +
                "|  __\\ \\ / / _ \\ '_ \\| __| \\ \\/ /\n" +
                "| |___\\ V /  __/ | | | |_| |>  < \n" +
                "\\____/ \\_/ \\___|_| |_|\\__|_/_/\\_\\\n" +
                "                                 \n" +
                "                                 \n" + ConfigurationManager.RESET);
        //Main loop to handle the user input and actions
        while(true){
            System.out.println("Please select an option: ");
            System.out.println("** start-program**");
            System.out.println("** configure **");
            System.out.println("** end-program **");
            System.out.println("** help **");
            System.out.print("Please select from the following: ");

            String choice  = input.next();

            switch (choice){
                case "start-program":
                    if (config == null){
                        ConfigurationManager.errorMessage("Please configure the system first");
                    }
                    else{
                        startProgram(config,input); //Start the program using the configured settings
                    }
                    break;
                case "configure":
                    //Configure the system and initialize the Configuration object
                    config = configureSystem(input, configurationManager);
                    break;
                case "end-program":
                    System.exit(0); //Exits the program
                    break;
                case "help":
                    displayHelp(); //Display help information
                    break;

                default:
                    ConfigurationManager.errorMessage("Invalid choice. Please try again");
            }
        }
    }

    /**
     * Validates user input to ensure its a positive integer
     *
     * @param input the Scanner object to read the user input
     * @return A valid positive integer entered by the user
     */

    public static int inputValidation(Scanner input){
        int number;
        while(true){
            try{
                number = input.nextInt();
                if(number > 0){
                    return number;//Will return from the method and execute the program
                }
                else {
                    ConfigurationManager.errorMessage("The number should be greater than 0");
                }
            } catch (Exception e) {
                ConfigurationManager.errorMessage("Invalid input. Please enter a valid integer: ");
                input.next(); //Clear the invalid inpuy
            }
        }
    }

    /**
     * Starts the program by creating and managing vendor and customer threads
     *
     * @param config The Configuration object containing the system parameters.
     * @param input the Scanner object to read user input*/

    public static void startProgram(Configuration config, Scanner input){
        int maxCapacity = config.getMaxTicketCapacity();
        int totalTickets = config.getTotalTickets();
        int ticketReleaseRate = config.getTicketReleaseRate();
        int customerRetrievalRate = config.getCustomerRetrievalRate();

        //Creating the Ticketpool object
        TicketPool ticketPool = new TicketPool(maxCapacity, totalTickets);

        System.out.println("Enter the number of vendors: ");
        int numberOfVendors = inputValidation(input);

        System.out.println("Enter number of customers: ");
        int numberOfCustomers = inputValidation(input);

        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();
        //Initialize and start the vendor threads
        for(int i =1; i <=numberOfVendors; i++){
            Thread vendorThread = new Thread(new Vendor(ticketPool, ticketReleaseRate,400, i));
            vendorThreads.add(vendorThread);
            vendorThread.start();
            System.out.println("Vendor " + i + " started.");
        }
        for(int i =1; i <= numberOfCustomers; i++){
            Thread customerThread = new Thread(new Customer(ticketPool, customerRetrievalRate,300, i));
            customerThreads.add(customerThread);
            customerThread.start();
            System.out.println("Customer " + i + " started.");
        }

        /**
         * Join the threads to ensure they complete execution
         */
        // Wait for vendor threads to complete
        try {
            for (Thread vendorThread : vendorThreads) {
                vendorThread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted while waiting for vendor threads to complete.");
            Thread.currentThread().interrupt();
        }

        // Wait for customer threads to complete
        try {
            for (Thread customerThread : customerThreads) {
                customerThread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted while waiting for customer threads to complete.");
            Thread.currentThread().interrupt();
        }

        //Signals that all the threads have completed
        System.out.println("All vendor and customer threads have completed.");
    }
    /**
     * Displays the help information to guide the user */
    public static void displayHelp() {
        System.out.println("Help Information:");
        System.out.println(" Start Program - Start the ticketing system.");
        System.out.println(" Configure - Configure system parameters like max capacity, release rate, etc.");
        System.out.println(" End Program - Exit the application.");
        System.out.println(" Help - Display this help information.");
    }

    /*Synchronization ensures thread-safe operations when accessing shared resources like the ticketpool and
    * notifyAll ensures that threads waiting on the ticketpool are awakend when
    * conditions change*/
}