package com.example.creditapplicationservice.infrastructure.adapter.out.persistence;

import com.example.creditapplicationservice.domain.model.CreditApplication;
import com.example.creditapplicationservice.domain.port.out.ApplicationRepositoryPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class ApplicationRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        // We disable H2 dialect and use Postgres
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.flyway.enabled", () -> "true");
    }

    @Autowired
    private ApplicationRepositoryPort applicationRepositoryPort;

    @Test
    void findAll_ShouldReturnInitialData_FromFlyway() {
        // Act
        List<CreditApplication> applications = applicationRepositoryPort.findAll();

        // Assert
        assertThat(applications).isNotEmpty();
        // Juan had 4 applications, María 1 = Total 5 (according to your last script)
        assertThat(applications).hasSizeGreaterThanOrEqualTo(5);
    }
}