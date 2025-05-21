package com.sweetshop.attendance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@ConfigurationPropertiesScan 
public class AttendanceApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
            .directory("/app") // Look in container's /app directory
            .ignoreIfMissing()
            .load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
        SpringApplication.run(AttendanceApplication.class, args);
    }
}
