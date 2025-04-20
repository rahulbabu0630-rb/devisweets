package com.sweetshop.attendance.config;

import java.util.Arrays;
import org.springframework.http.HttpMethod;
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
        
        // Allowed frontend origins
        config.setAllowedOrigins(Arrays.asList(
            "https://devisweets1.vercel.app",
            "https://durgadevisweets.vercel.app",
            "http://localhost:5173",
            "https://newrepo-rose.vercel.app"
        ));
        
        // Allowed methods
        config.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));
        
        // Allowed headers
        config.setAllowedHeaders(Arrays.asList(
            "Origin", "Content-Type", "Accept", "Authorization",
            "X-Requested-With", "Cache-Control"
        ));
        
        config.setExposedHeaders(Arrays.asList(
            "Authorization", "Content-Disposition"
        ));
        
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        
        // Apply to specific paths
        source.registerCorsConfiguration("/api/**", config);
        source.registerCorsConfiguration("/employees/**", config);
        
        return new CorsFilter(source);
    }
}