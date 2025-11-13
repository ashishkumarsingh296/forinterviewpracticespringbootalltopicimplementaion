package com.example.forinterviewpracticespringbootalltopicimplementaion.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;

@Configuration
public class OpenApiYamlConfig {

    @Bean
    @Primary
    public OpenAPI customOpenApi() {
        Yaml yaml = new Yaml();
        InputStream yamlStream = getClass().getClassLoader()
                .getResourceAsStream("swagger/swagger.yml");

        if (yamlStream == null) {
            throw new RuntimeException("swagger.yml not found!");
        }

        OpenAPI openAPI = yaml.loadAs(yamlStream, OpenAPI.class);

        Components components = openAPI.getComponents();
        if (components == null) {
            components = new Components();
        }

        components.addSecuritySchemes("bearerAuth",
                new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
        );

        openAPI.setComponents(components);

        SecurityRequirement securityItem = new SecurityRequirement();
        securityItem.addList("bearerAuth");

        openAPI.setSecurity(List.of(securityItem));

        return openAPI;
    }


}
