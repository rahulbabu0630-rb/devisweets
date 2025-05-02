package com.sweetshop.attendance.config;

import java.util.Arrays;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "https://newrepo-rose.vercel.app",
                "https://devisweets1.vercel.app",
                "https://durgadevisweets.vercel.app",
                "https://durgadevisweets.onrender.com",
                "http://localhost:5173"
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .exposedHeaders("Authorization", "Content-Disposition")
            .allowCredentials(true)
            .maxAge(3600);
    }

        @Bean
        public CorsFilter corsFilter() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.addAllowedOrigin("https://durgadevisweets.vercel.app");
            config.addAllowedOrigin("https://devisweets1.vercel.app");
            config.addAllowedOrigin("https://newrepo-rose.vercel.app");
            config.addAllowedOrigin("https://durgadevisweets.onrender.com");
            config.addAllowedOrigin("http://localhost:5173");
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            config.addExposedHeader("Authorization");
            source.registerCorsConfiguration("/**", config);
            return new CorsFilter(source);
        }
}