package com.example.IITCW.Service;

import com.example.IITCW.Entities.SystemConfiguration;
import com.example.IITCW.Entities.Ticket;
import com.example.IITCW.Repository.SystemConfigurationRepository;
import com.example.IITCW.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SystemConfigurationService {
    private boolean isSystemRunning = false;

    @Autowired
    private SystemConfigurationRepository configRepository; //It tells Spring to automatically inject as an instance of SystemConfigrepo into the configrepo field

    @Autowired
    private TicketRepository ticketRepository;
    //Save or update a configuration
    public SystemConfiguration saveConfiguration(SystemConfiguration config){
        return configRepository.save(config);
    }

    //Get the current configuration (assume there's only one record for simplicity)
    public SystemConfiguration getConfiguration(){
        return configRepository.findAll().stream().findFirst().orElse(null);
    }
    // Start ticket release logic
    public void startSystem() {
        SystemConfiguration config = getConfiguration();

        if (config == null) {
            throw new RuntimeException("System configuration not found. Please set the configuration first.");
        }

        isSystemRunning = true;
        System.out.println("System started.");
    }

    // Stop ticket release logic
    public void stopSystem() {
        isSystemRunning = false;
        System.out.println("System stopped.");
    }

    // Scheduled ticket release logic (runs every second)
    @Scheduled(fixedRate = 1000)
    public void releaseTickets() {
        if (!isSystemRunning) {
            return; // Exit if the system is not running
        }

        SystemConfiguration config = getConfiguration();
        if (config == null) {
            System.out.println("No configuration found. Stopping system.");
            isSystemRunning = false;
            return;
        }

        int releaseRate = config.getTicketReleaseRate();
        System.out.println("Releasing " + releaseRate + " tickets...");
        // Add your logic here to update ticket availability in the database
    }
    /*public Map<String, Object> getSystemStatus() {
        // Fetch ticket availability
        List<Ticket> tickets = ticketRepository.findAll();
        long availableTickets = tickets.stream()
                .filter(ticket -> ticket.getStatus() && ticket.getQuantity() > 0)
                .count();

        // Mock system status
        String systemStatus = "Running"; // You can set this based on actual business logic

        // Prepare the response
        Map<String, Object> status = new HashMap<>();
        status.put("ticketAvailability", availableTickets);
        status.put("systemStatus", systemStatus);
        status.put("logs", logs);

        return status;
    }*/
}