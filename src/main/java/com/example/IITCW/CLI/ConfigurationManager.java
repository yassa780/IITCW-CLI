package com.example.IITCW.CLI;
import java.io.*;

import com.google.gson.Gson;

public class ConfigurationManager {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String Green = "\u001B[32m";

    private static final String SERIALIZED_FILE = "config.ser";/*Defines the filenames used for saving the configuration*/
    private static final String JSON_FILE = "config.json";

    //public ConfigurationManager() {}

    //Save the configuration object using Java Serialization
    public static void saveSerialized(Configuration config){
        String path = "src/main/resources/" + SERIALIZED_FILE;
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path)))//Used to serialize the object and save it to a file
        {
            out.writeObject(config); //Serialize and save
            successMessage("Configuration saved as binary to " + path);
        }
        catch(IOException e){
            errorMessage("Error saving configuration as binary: " + e.getMessage());/*If an exception occurs it catches the IOException and prints
            an error message*/
        }
    }
    //Write the configuration object to a JSON file using Gson
    public void writeJson(Configuration config){
        Gson gson = new Gson();
        String path = "src/main/resources/" + JSON_FILE;
        try(FileWriter writer = new FileWriter(path)){
            gson.toJson(config, writer); //Converts the config object into a JSON string and writes it into a file
            successMessage("Configuration written to configuration file " + JSON_FILE + " successfully");
        }
        catch(IOException e){
            errorMessage("Error writing the configuration in JSON format: " + e.getMessage());
        }
    }

    //Read the configuration object from a JSON file using Gson
    public static Configuration readJson(){
        Gson gson = new Gson();
        String path = "src/main/resources/" + JSON_FILE;
        try(FileReader reader = new FileReader(path)){
            successMessage("Configuation read from Json file");
            return gson.fromJson(reader, Configuration.class);
        }
        catch(IOException e){
            errorMessage("Error reading configuration from file: " + e.getMessage());

            return null;
        }
    }
    public static void errorMessage(String message){
        System.out.println(RED + message + RESET);
    }

    public static void successMessage(String message){
        System.out.println(Green + message + RESET);
    }

    /**
     * If we use static method, we should call the respective method from the classNAme object, but if we dont use static
     * we can call the that specific method by creating an instance on the class that we want to call the specific function
     * **/


}
