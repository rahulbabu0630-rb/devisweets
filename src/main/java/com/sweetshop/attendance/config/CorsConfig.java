package com.sweetshop.attendance.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Allow all your frontend domains (including local development)
        config.setAllowedOrigins(Arrays.asList(
            "https://devisweets1.vercel.app",
            "https://durgadevisweets.vercel.app",
            "http://localhost:5173",  // Note: changed from https to http for local dev
            "https://durgadevisweets.onrender.com"  // Add your backend domain if needed
        ));
        
        // Allow all standard HTTP methods
        config.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));
        
        // Allow all headers you need
        config.setAllowedHeaders(Arrays.asList(
            "Origin", "Content-Type", "Accept", "Authorization",
            "X-Requested-With", "Cache-Control", "If-Modified-Since"
        ));
        
        // Expose any custom headers if needed
        config.setExposedHeaders(Arrays.asList(
            "Authorization", "Content-Disposition"
        ));
        
        // Allow credentials
        config.setAllowCredentials(true);
        
        // Set max age to reduce preflight requests
        config.setMaxAge(3600L);
        
        // Apply this configuration to all paths
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}