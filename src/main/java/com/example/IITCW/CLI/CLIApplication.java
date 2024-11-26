package com.example.IITCW.CLI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.IITCW.CLI.Configuration.configureSystem;


public class CLIApplication  {



    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {//This is the main thread
        Scanner input = new Scanner(System.in);
        ConfigurationManager configurationManager = new ConfigurationManager();
        Configuration config = null;


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

        while(true){
            System.out.println("Please select an option: ");
            System.out.println("1. Start-program");
            System.out.println("2. Configure");
            System.out.println("3. end-program");
            System.out.println("4. Help");
            System.out.print("Enter your choice: ");

            int choice  = input.nextInt();

            switch (choice){
                case 1:
                    if (config == null){
                        ConfigurationManager.errorMessage("Please configure the system first");
                    }
                    else{
                        startProgram(config,input); //Pass the configuration and Scanner
                    }
                    break;
                case 2:
                    //Configure the system and initialize the Configuration object
                    config = configureSystem(input, configurationManager);
                    break;
                case 3:
                    System.exit(0);
                    break;
                case 4:
                    displayHelp();
                    break;

                default:
                    ConfigurationManager.errorMessage("Invalid choice. Please try again");
            }
        }
    }

    //Collect and validate inputs
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
                input.next();
            }
        }
    }


    public static void startProgram(Configuration config, Scanner input){
      int maxCapacity = config.getMaxTicketCapacity();
      int ticketReleaseRate = config.getTicketReleaseRate();
      int customerRetrievalRate = config.getCustomerRetrievalRate();

      //Creating the Ticketpool object
      TicketPool ticketPool = new TicketPool(maxCapacity);

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

        //Join the threads to ensure they complete execution
        // Wait for vendor threads to complete
        try {
            for (Thread vendorThread : vendorThreads) {
                vendorThread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted while waiting for vendor threads to complete.");
            Thread.currentThread().interrupt();
        }

        // Signal that ticket selling is complete
        synchronized (ticketPool) {
            ticketPool.setSellingComplete(true);
            ticketPool.notifyAll(); // Notify waiting customer threads
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

        System.out.println("All vendor and customer threads have completed.");
    }

    public static void displayHelp() {
        System.out.println("Help Information:");
        System.out.println("1: Start Program - Start the ticketing system.");
        System.out.println("2: Configure - Configure system parameters like max capacity, release rate, etc.");
        System.out.println("3: End Program - Exit the application.");
        System.out.println("4: Help - Display this help information.");
    }

    /*Synchronization ensures thread-safe operations when accessing shared resources like the ticketpool and
    * notifyAll ensures that threads waiting on the ticketpool are awakend when
    * conditions change*/
}