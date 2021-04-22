package com.rulo.vinted.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Vinted API")
                        .description("Portal de documentación, ejemplos y uso de la API -> vinted <-")
                        .contact(new Contact()
                                .name("Raúl Arriaga")
                                .email("raul.arriaga.dev@gmail.com")
                                .url("https://github.com/ruloCodes"))
                        .version("1.0"));
    }
}