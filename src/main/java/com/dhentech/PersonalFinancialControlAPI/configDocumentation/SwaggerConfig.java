package com.dhentech.PersonalFinancialControlAPI.configDocumentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Personal Financial Control API",
                version = "1.0",
                description = "PersonalFinancialControlAPI is a RESTful API for recording and managing personal expenses. It allows users to add, update, delete, and view transactions, storing all data in a database for easy tracking and organization of spending.",
                contact = @Contact(
                        name = "Denilson Souza",
                        email = "denilson_contato@outlook.com",
                        url = "https://github.com/DhenSouza/PersonalFinancialControlAPI"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Development Server")
        }
)
public class SwaggerConfig {
}
