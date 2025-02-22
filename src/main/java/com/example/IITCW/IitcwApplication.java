package com.example.IITCW;

import com.example.IITCW.CLI.Configuration;
import com.example.IITCW.CLI.ConfigurationManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class IitcwApplication {

	public static final String YELLOW = "\u001B[33m";

	public static void main(String[] args) {
		//SpringApplication.run(IitcwApplication.class, args);
		Scanner input = new Scanner(System.in);
		ConfigurationManager configurationManager = new ConfigurationManager();

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

		//Collect and validate inputs
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
}
