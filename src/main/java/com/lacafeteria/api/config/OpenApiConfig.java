package com.lacafeteria.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI lacafeteriaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("La Cafetería – API REST")
                        .description(
                                "API para la gestión de pedidos, productos, empleados, clientes, facturas y reportes de La Cafetería.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("La Cafetería")
                                .email("soporte@lacafeteria.com")));
    }
}
