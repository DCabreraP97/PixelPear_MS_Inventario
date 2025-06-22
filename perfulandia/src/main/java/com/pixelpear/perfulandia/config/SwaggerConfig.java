package com.pixelpear.perfulandia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Perfulandia API")
                        .version("2.0.1")
                        .description("Documentación de la API Restful de Perfulandia, microservicio de inventario.")
                        .contact(new Contact()
                                .name("Equipo PixelPear")
                                .email("soporte@pixelpear.cl")
                                .url("https://pixelpear.cl")
                        )
                );
        }
}
