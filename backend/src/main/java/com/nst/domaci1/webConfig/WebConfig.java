package com.nst.domaci1.webConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Omogućava sve putanje
                .allowedOrigins("http://localhost:4200") // Dozvoljava zahteve sa vašeg frontenda
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
    }
}
