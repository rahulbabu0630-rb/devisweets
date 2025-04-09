package com.sweetshop.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@ConfigurationPropertiesScan // Add this to ensure proper property binding
public class AttendanceApplication {
    public static void main(String[] args) {
        // Load .env file before Spring starts
        Dotenv dotenv = Dotenv.configure()
            .directory("/app") // Look in container's /app directory
            .ignoreIfMissing()
            .load();
        
        // Set system properties for Spring to use
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });

        SpringApplication.run(AttendanceApplication.class, args);
    }
}