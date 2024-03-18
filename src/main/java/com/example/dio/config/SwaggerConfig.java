package com.example.dio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Titulo da API")
                        .description("Descrição da API")
                        .summary("Resumo da api")
                        .version("versão")
                        .contact(new Contact()
                                .name("nome para contato")
                                .email("email para contato")
                                .url("site de contato")
                        )
                        .license(new License()
                                .name("Nome linceça")
                                .url("link da linceça")
                        )
                );
    }
}
