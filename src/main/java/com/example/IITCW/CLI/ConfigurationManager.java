package com.example.IITCW.CLI;
import java.io.*;

import com.google.gson.Gson;

/**
 * This class is responsible for saving, loading, and managing configuration objects
 * It supports serialization to binary files and writing/reading configurations in JSON format
 */
public class ConfigurationManager {

    //ANSI esacpe codes for colour-coded console messages
    public static final String RESET = "\u001B[0m"; // Reset the text colour to default
    public static final String RED = "\u001B[31m"; //Red text for error messages
    public static final String Green = "\u001B[32m"; //Green text for success messages

    //File paths for saving serializations and JSON configurations
    private static final String SERIALIZED_FILE = "config.ser";/*Defines the filenames used for saving the configuration*/
    private static final String JSON_FILE = "config.json"; //JSON configuation file

    /**
     * Saves the configuration object using JAVA Serialization
     * @param config The configuration object to save
     */
    public static void saveSerialized(Configuration config){
        String path = "src/main/resources/" + SERIALIZED_FILE;
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path)))//Used to serialize the object and save it to a file
        {
            out.writeObject(config); //Serialize the configuration object and save it to a file
            successMessage("Configuration saved as binary to " + path);
        }
        catch(IOException e){
            errorMessage("Error saving configuration as binary: " + e.getMessage());/*If an exception occurs it catches the IOException and prints
            an error message*/
        }
    }

    /**
     * Writes the configuration object to a JSON file using Gson
     *
     * @param config The configuration object to save as JSON
     */
    public void writeJson(Configuration config){
        Gson gson = new Gson();
        String path = "src/main/resources/" + JSON_FILE; //Define the file path
        try(FileWriter writer = new FileWriter(path)){
            gson.toJson(config, writer); //Converts the config object into a JSON string and writes it into a file
            successMessage("Configuration written to configuration file " + JSON_FILE + " successfully");
        }
        catch(IOException e){
            errorMessage("Error writing the configuration in JSON format: " + e.getMessage());
        }
    }

    /**
     * Reads the configuration object from a JSON file using Gson
     *
     * @return The configuration object read from JSON file, or null if an error occurs.
     */
    public static Configuration readJson(){
        Gson gson = new Gson();
        String path = "src/main/resources/" + JSON_FILE; //Define the file path
        try(FileReader reader = new FileReader(path)){
            //Read the configuration object from the JSON file
            successMessage("Configuation read from Json file");
            return gson.fromJson(reader, Configuration.class);
        }
        catch(IOException e){
            errorMessage("Error reading configuration from file: " + e.getMessage());

            return null;
        }
    }

    /**
     * Displays an error message in red colour
     * @param message The error message to display
     */
    public static void errorMessage(String message){
        System.out.println(RED + message + RESET);
    }

    /**
     * Displays a success message in green colour
     * @param message
     */
    public static void successMessage(String message){
        System.out.println(Green + message + RESET);
    }

    /**
     * If we use static method, we should call the respective method from the classNAme object, but if we dont use static
     * we can call the that specific method by creating an instance on the class that we want to call the specific function
     * **/


}
