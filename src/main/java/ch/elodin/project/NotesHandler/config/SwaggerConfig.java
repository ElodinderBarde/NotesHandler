package ch.elodin.project.NotesHandler.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Notes Handler API")
                        .version("1.0.0")
                        .description("REST API für NotesHandler-Anwendung mit CRUD-Operationen für Notizen und Benutzerverwaltung.")
                        .contact(new Contact()
                                .name("Notes")
                                .email("nope@wiss.ch")));
    }
}