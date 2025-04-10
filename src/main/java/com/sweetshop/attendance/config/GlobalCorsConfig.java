package com.sweetshop.attendance.config;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.Arrays;

@Configuration
public class GlobalCorsConfig {

    // Production configuration (uses application.properties)
    @Value("${cors.allowed-origins:http://localhost:5173}")
    private String[] allowedOrigins;
    
    @Value("${cors.allowed-origin-patterns:}")
    private String[] allowedOriginPatterns;
    
    @Value("${cors.allowed-methods:*}")
    private String[] allowedMethods;
    
    @Value("${cors.allowed-headers:*}")
    private String[] allowedHeaders;
    
    @Value("${cors.allow-credentials:true}")
    private boolean allowCredentials;
    
    @Value("${cors.max-age:3600}")
    private long maxAge;
    
    @Value("${cors.exposed-headers:}")
    private String[] exposedHeaders;

    // Main CORS filter (active in all profiles)
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Set properties from application.properties
        config.setAllowedOrigins(Arrays.asList(allowedOrigins));
        config.setAllowedOriginPatterns(Arrays.asList(allowedOriginPatterns));
        config.setAllowedMethods(Arrays.asList(allowedMethods));
        config.setAllowedHeaders(Arrays.asList(allowedHeaders));
        config.setAllowCredentials(allowCredentials);
        config.setMaxAge(maxAge);
        config.setExposedHeaders(Arrays.asList(exposedHeaders));
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    // Local development override (simpler config)
    @Profile("local")
    @Bean
    public CorsFilter localCorsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}