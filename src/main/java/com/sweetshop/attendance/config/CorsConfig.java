package com.sweetshop.attendance.config;

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
        
        // Allow all your domains + local development
        config.setAllowCredentials(true);
        config.addAllowedOrigin("https://durgadevisweets.vercel.app");
        config.addAllowedOrigin("https://devisweets1.vercel.app");
        config.addAllowedOrigin("https://newrepo-rose.vercel.app");
        config.addAllowedOrigin("https://durgadevisweets.onrender.com");
        config.addAllowedOrigin("http://localhost:5173");
        
        // For development, you might want to add:
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://localhost:3000");
        
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Content-Disposition");
        config.setMaxAge(3600L);
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}