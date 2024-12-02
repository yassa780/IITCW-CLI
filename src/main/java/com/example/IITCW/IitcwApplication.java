package com.example.IITCW;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IitcwApplication {
    public static void main(String[] args) {
        SpringApplication.run(IitcwApplication.class, args);
    }

}
