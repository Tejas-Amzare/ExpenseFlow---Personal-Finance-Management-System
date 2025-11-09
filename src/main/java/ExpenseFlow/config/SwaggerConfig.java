package ExpenseFlow.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ExpenseFlow API")
                        .version("1.0.0")
                        .description("""
                                ExpenseFlow is a comprehensive Personal Finance Management System built with Spring Boot. 
                                It provides REST APIs for managing expenses, budgets, categories, and financial analytics.
                                
                                ### Core Features:
                                - User Authentication & JWT-based security
                                - Role-based access control (USER, ADMIN)
                                - Expense CRUD operations with category & payment method
                                - Budget management with tracking and analytics
                                - Financial analytics: monthly/yearly reports, category-wise spending, savings calculations
                                
                                ### Technology Stack:
                                - Backend: Spring Boot 3.5.6
                                - Language: Java 17
                                - Database: MySQL 8.0
                                - ORM: Spring Data JPA / Hibernate
                                - Security: Spring Security + JWT
                                - API Docs: Swagger/OpenAPI 3
                                
                                ### Usage:
                                All endpoints (except /api/auth/**) require JWT Bearer token.
                                Include in header: `Authorization: Bearer <token>`""")
                        .termsOfService("https://example.com/terms")
                        .contact(new Contact()
                                .name("ExpenseFlow Support")
                                .email("support@example.com")
                                .url("https://example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT Authentication: Include 'Bearer {token}' in Authorization header")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
