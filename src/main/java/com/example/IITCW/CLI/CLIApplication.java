package com.example.IITCW.CLI;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLIApplication {
    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {//This is the main thread
        Scanner input = new Scanner(System.in);
        ConfigurationManager configurationManager = new ConfigurationManager();
        TicketPool ticketPool = new TicketPool(20); //Created the ticketpool object

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
                    startProgram(ticketPool, input);
                    break;
                case 2:
                    configureSystem(input, configurationManager);
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
    public static int InputValidation(Scanner input){
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

    public static void configureSystem(Scanner input, ConfigurationManager configurationManager ){
        System.out.print("Enter the total no. of tickets: ");
        int totalTickets = InputValidation(input);

        System.out.print("Enter ticket release date: ");
        int ticketReleaseDate = InputValidation(input);

        System.out.print("Enter customer retrieval date: ");
        int customerRetrievalDate = InputValidation(input);

        System.out.print("Enter the maximum ticket Capacity: ");
        int maximunTicketCapacity = InputValidation(input);

        //Output the results
        System.out.println();
        System.out.println("Configuration created successfully: ");
        System.out.println("Total tickets: " + totalTickets);
        System.out.println("Ticket release date: " + ticketReleaseDate);
        System.out.println("Customer retrieval date: " + customerRetrievalDate);
        System.out.println("Maximum ticket capacity: " + maximunTicketCapacity);

        Configuration config = new Configuration(totalTickets,ticketReleaseDate,customerRetrievalDate,maximunTicketCapacity);
        configurationManager.writeJson(config);
    }

    public static void startProgram(TicketPool ticketPool, Scanner input){

        //Lists to manage Vendor and Customer Threads
        List<Vendor> vendors = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();

        //Vendor Details
        System.out.println("Enter the number of vendors present: ");
        int vendorCount = InputValidation(input);

        if(vendorCount <= 0){
            ConfigurationManager.errorMessage("Error: You must have at least one vendor present");
            return;
        }

        for(int i = 0; i < vendorCount; i++){
            System.out.println("Enter details for vendor " + i + ": ");
            System.out.println("Vendor Id: ");
            String vendorId = input.next();

            System.out.println("Number of tickets to release: ");
            int ticketsPerRelease = InputValidation(input);

            System.out.println("Release interval: ");
            int releaseInterval = InputValidation(input);

            Vendor vendor = new Vendor(vendorId, ticketsPerRelease, releaseInterval, ticketPool);
            vendors.add(vendor);
            Thread vendorThread = new Thread(vendor);
            vendorThreads.add(vendorThread);
            //vendorThread.start();

        }

        //The customer details
        System.out.println("Enter the number of customers: ");
        int customerCount = InputValidation(input);
        if(customerCount <= 0){
            ConfigurationManager.errorMessage("Error!: You must have atleast one customer present");
            return;
        }

        for(int i = 1; i <= customerCount; i++){
            System.out.println("customer " + i + ":");
            System.out.println("customer Id: ");
            String customerId = input.next();

            System.out.println("Number of tickets to buy per attempt: ");
            int ticketsToBuy =InputValidation(input);

            System.out.println("Retrieval interval: ");
            int retrievalInterval = InputValidation(input);

            Customer customer = new Customer(customerId, retrievalInterval, ticketsToBuy, ticketPool);
            customers.add(customer);
            Thread customerThread = new Thread(customer);
            customerThreads.add(customerThread);
           // customerThread.start();
        }

        for(Thread vendorThread: vendorThreads) {
            vendorThread.start();
        }
        for(Thread customerThread : customerThreads){
            customerThread.start();
        }
        //Monitor ticketpoool and stop threads when tickets are sold out
        while (true){
            if(ticketPool.getTicketCount() == 0){
                for(Vendor vendor : vendors){
                    vendor.stop();
                }
                for(Customer customer: customers){
                    customer.stop();
                }
                break;
            }
        }

        //Join Threads
        try{
            for(Thread thread: vendorThreads){
                thread.join();
            }
            for(Thread thread: customerThreads){
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread interuppted during thread joining.");
            Thread.currentThread().interrupt();
        }

        //Final output
        System.out.println("\nFinal ticket pool status: ");
        System.out.println("Tickets remaining in pool: " + ticketPool.getTicketCount());

        for(Vendor vendor: vendors){
            System.out.println("Vendor " + vendor.getVendorId() + " released " + vendor.getTotalTicketsReleased() + " tickets");
        }
        for(Customer customer: customers){
            System.out.println("Customer " + customer.getCustomerId() + " purchased " + customer.getTotalTicketsPurchased() + " tickets");
        }

    }

   /* public static void startProgram(TicketPool ticketPool){
        //Creating Vendor objects with different release intervals and ticket amounts
        Vendor vendor1 = new Vendor("1", 5,2000,ticketPool);
        Vendor vendor2 = new Vendor("2",3,1500,ticketPool);
        Vendor vendor3 = new Vendor("3",3,1600,ticketPool);
        Vendor vendor4 = new Vendor("4",3,1700,ticketPool);

        Customer customer1 = new Customer("1", 1000, 3, ticketPool);
        Customer customer2 = new Customer("2", 300, 5, ticketPool);
        Customer customer3 = new Customer("3", 300,1, ticketPool);
        Customer customer4 = new Customer("3", 500,1, ticketPool);

        //Create threads for each vendor
        Thread vendorThread1 = new Thread(vendor1);
        Thread vendorThread2 = new Thread(vendor2);
        Thread vendorThread3 = new Thread(vendor3);
        Thread vendorThread4 = new Thread(vendor4);

        //Starting each vendor thread
        vendorThread1.start();
        vendorThread2.start();
        vendorThread3.start();
        vendorThread4.start();

        //Create threads for each Customer
        Thread customerThread1 = new Thread(customer1);
        Thread customerThread2 = new Thread(customer2);
        Thread customerThread3 = new Thread(customer3);
        Thread customerThread4 = new Thread(customer4);

        //Starting each customer thread
        customerThread1.start();
        customerThread2.start();
        customerThread3.start();
        customerThread4.start();

        while (true){
            if (ticketPool.getTicketCount() == 0){
                vendor1.stop();
                vendor2.stop();
                vendor3.stop();
                vendor4.stop();

                customer1.stop();
                customer2.stop();
                customer3.stop();
                customer4.stop();
                break;
            }
        }

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            System.out.println("Main thread interrupted during sleep");
            Thread.currentThread().interrupt();
        }
        System.out.println();

        //Joining the threads for proper termination
        /* The join() ensures all threads have completed their work before the main threads proceeds*/

        /*try{
            vendorThread1.join();//It will pause the main thread and start executing the sub threads
            vendorThread2.join();
            vendorThread3.join();
            vendorThread4.join();

            customerThread1.join(); //It will pause the main thread and start executing the sub threads
            customerThread2.join();
            customerThread3.join(); //It will pause the main thread and start executing the sub threads
            customerThread4.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //The final output
        System.out.println("Total tickets released by Vendor 1: " + vendor1.getTotalTicketsReleased());
        System.out.println("Total tickets released by Vendor 2: " + vendor2.getTotalTicketsReleased());
        System.out.println("Total tickets released by Vendor 3: " + vendor3.getTotalTicketsReleased());
        System.out.println("Total tickets released by Vendor 4: " + vendor4.getTotalTicketsReleased());
        System.out.println("Final tickets in pool: " + ticketPool.getTicketCount());
        System.out.println();

        //The final output
        System.out.println("Total tickets purchased by customer 1: " + customer1.getTotalTicketsPurchased());
        System.out.println("Total tickets purchased by customer 2: " + customer2.getTotalTicketsPurchased());
        System.out.println("Total tickets purchased by customer 3: " + customer3.getTotalTicketsPurchased());
        System.out.println("Total tickets purchased by customer 4: " + customer4.getTotalTicketsPurchased());
        System.out.println();
    }*/

    public static void displayHelp() {
        System.out.println("Help Information");
        System.out.println("Enter 1 to start the program");
        System.out.println("Enter 2 to configure the ticketing system");
        System.out.println("Enter 3 to terminate the program");
        System.out.println();
    }
}
