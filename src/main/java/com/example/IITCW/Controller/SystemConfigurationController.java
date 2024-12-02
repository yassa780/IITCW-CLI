package com.example.IITCW.Controller;

import com.example.IITCW.Entities.SystemConfiguration;
import com.example.IITCW.Service.SystemConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//The controller will expose rest endpoints for the frontend to interact with the backend(e.g GET, POST...)


@RestController
@RequestMapping("/api/system")
public class SystemConfigurationController {
    @Autowired
    private SystemConfigurationService configService;

    //Endpoint to save a configuration
    @PostMapping("/config")
    public SystemConfiguration saveConfiguration(@RequestBody SystemConfiguration config){
        return configService.saveConfiguration(config);
    }

    //Endpoint to fetch the current configuration
    @GetMapping("/config")
    public SystemConfiguration getConfiguration() {
        return configService.getConfiguration();
    }
    // Start the system
    @PostMapping("/start")
    public ResponseEntity<String> startSystem() {
        configService.startSystem();
        return ResponseEntity.ok("System started.");
    }

    // Stop the system
    @PostMapping("/stop")
    public ResponseEntity<String> stopSystem() {
        configService.stopSystem();
        return ResponseEntity.ok("System stopped.");
    }


}
