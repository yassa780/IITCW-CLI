package com.example.IITCW.CLI;

import javax.imageio.IIOException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String Log_File = "system.log";// Log file name
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Log a message to both the console and file

    public static void log(String message) {
        String timeStamp = LocalDateTime.now().format(FORMATTER);
        String logMessage = timeStamp + '-' + message;

        //Log to console
        System.out.println(logMessage);

        //Log to file
      /*  try(FileWriter writer = new FileWriter(Log_File, true)){
            writer.write(logMessage + System.lineSeparator());
        }
        catch (IOException e){
            System.out.println("Error writing to log file: " + e.getMessage());
        }*/
    }

    //Log error messages
    public static void logError(String message){
        log(message);
    }

    //Log info messages
    public static void info(String message){
        log(message);
    }

    //Log debug messages
    public static void debug(String message){
        log(message);
    }
}
