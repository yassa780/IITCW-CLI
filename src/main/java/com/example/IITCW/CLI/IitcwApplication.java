package com.example.IITCW.CLI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class IitcwApplication {

	public static final String YELLOW = "\u001B[33m";

	public static void main(String[] args) {//This is the main thread
		//SpringApplication.run(IitcwApplication.class, args);
		Scanner input = new Scanner(System.in);
		ConfigurationManager configurationManager = new ConfigurationManager();
		TicketPool ticketPool = new TicketPool(50); //Created the ticketpool object

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

		System.out.println("Please select an option: ");
		System.out.println("1. Configure");
		System.out.println("2. Vendor");
		System.out.println("3. Customer");
		System.out.print("Enter your choice: ");

		int choice  = input.nextInt();

		switch (choice){
			case 1:
				configureSystem(input, configurationManager);
				break;
			case 2:
				vendorSystem(ticketPool);
				break;
			case 3:
				customerSystem(ticketPool);
				break;
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
	public static void vendorSystem(TicketPool ticketPool){
		//Creating Vendor objects with different release intervals and ticket amounts
		Vendor vendor1 = new Vendor("1", 5,1000,ticketPool);
		Vendor vendor2 = new Vendor("2",3,1500,ticketPool);

		//Create threads for each vendor
		Thread vendorThread1 = new Thread(vendor1);
		Thread vendorThread2 = new Thread(vendor2);

		//Starting each vendor thread
		vendorThread1.start();
		vendorThread2.start();

		try{
			vendorThread1.join();//It will pause the main thread and start executing the sub threads
			vendorThread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//The final output
		System.out.println("Total tickets released by Vendor 1: " + vendor1.getTotalTicketsReleased());
		System.out.println("Total tickets released by Vendor 2: " + vendor2.getTotalTicketsReleased());
		System.out.println("Final tickets in pool: " + ticketPool.getTicketCount());
	}

	public static void customerSystem(TicketPool ticketPool){
		Customer customer1 = new Customer("customer1", 1000, 3, ticketPool);
		Customer customer2 = new Customer("customer2", 5000, 5, ticketPool);

		//Create threads for each Customer
		Thread customerThread1 = new Thread(customer1);
		Thread customerThread2 = new Thread(customer2);

		//Starting each customer thread
		customerThread1.start();
		customerThread2.start();

		try{
			customerThread1.join(); //It will pause the main thread and start executing the sub threads
			customerThread2.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//The final output
		System.out.println("Total tickets purchased by customer 1: " + customer1.getTotalTicketsPurchased());
		System.out.println("Total tickets purchased by customer 2: " + customer2.getTotalTicketsPurchased());
	}
}
