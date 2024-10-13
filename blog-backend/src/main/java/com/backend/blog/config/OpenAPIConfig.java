package com.backend.blog.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Add security globally (affecting all endpoints)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                // Add security scheme to the OpenAPI components section
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP) // Security type is HTTP-based (JWT)
                                .scheme("bearer") // Bearer token
                                .bearerFormat("JWT") // Token format (JWT)
                                .in(SecurityScheme.In.HEADER) // Specify that the token will be passed in the HTTP Header
                                .name("Authorization") // Name of the header where JWT is passed
                        )
                )
                // Optional: Add additional metadata like project info
                .info(new Info()
                        .title("Blogging App Backend")
                        .description("This project is developed by Nabyendu")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Nabyendu")
                                .url("https://nojha.in")
                                .email("nabyenduojha99@gmail.com"))
                );
    }
}