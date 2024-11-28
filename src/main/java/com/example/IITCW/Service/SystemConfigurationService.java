package com.example.IITCW.Service;

import com.example.IITCW.Entities.SystemConfiguration;
import com.example.IITCW.Repository.SystemConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigurationService {

    @Autowired
    private SystemConfigurationRepository configRepository; //It tells Spring to automatically inject as an instance of SystemConfigrepo into the configrepo field

    //Save or update a configuration
    public SystemConfiguration saveConfiguration(SystemConfiguration config){
        return configRepository.save(config);
    }

    //Get the current configuration (assume there's only one record for simplicity)
    public SystemConfiguration getConfiguration(){
        return configRepository.findAll().stream().findFirst().orElse(null);
    }

}