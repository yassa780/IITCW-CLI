package com.example.IITCW.CLI;

public class Comments {
     /*public static void startProgram(TicketPool ticketPool, Scanner input){

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

    }*/
}
