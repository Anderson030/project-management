# Credit Application Service

## Description

This project is a credit application service built with Spring Boot. It allows users to submit credit applications, which are then processed and stored in a database. The service is secured using Spring Security and JWT for authentication and authorization.

## Technologies Used

- **Java 21**: The core programming language.
- **Spring Boot**: Framework for creating stand-alone, production-grade Spring-based Applications.
- **Spring Data JPA**: For simplifying data access layers.
- **Spring Security**: For authentication and authorization.
- **PostgreSQL**: Production-ready relational database.
- **H2 Database**: In-memory database for testing and development.
- **Flyway**: For database migrations.
- **MapStruct**: For mapping between DTOs and domain models.
- **JWT (JSON Web Tokens)**: For securing the API.
- **Maven**: For dependency management and build automation.

## Getting Started

### Prerequisites

- JDK 21 or later
- Maven 3.2+
- Docker and Docker Compose (for running PostgreSQL)

### Installation

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd credit-application-service
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   The application is configured to use an in-memory H2 database by default.
   ```bash
   mvn spring-boot:run
   ```
   The application will be available at `http://localhost:8080`.

### Running with PostgreSQL

1. **Start the PostgreSQL database using Docker:**
   A `docker-compose.yml` file can be added to manage the database container.

2. **Configure the application to connect to PostgreSQL:**
   Update the `src/main/resources/application.properties` file with your PostgreSQL connection details:
   ```properties
   # spring.datasource.url=jdbc:postgresql://localhost:5432/yourdb
   # spring.datasource.username=youruser
   # spring.datasource.password=yourpassword
   # spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   ```

## API Endpoints

### Create a Credit Application

- **URL:** `/applications`
- **Method:** `POST`
- **Description:** Submits a new credit application.
- **Request Body:**
  ```json
  {
    "customerName": "John Doe",
    "creditAmount": 5000.00
  }
  ```
- **Success Response:**
  - **Code:** `201 CREATED`
  - **Content:**
    ```json
    {
      "id": "some-unique-id",
      "customerName": "John Doe",
      "creditAmount": 5000.00,
      "status": "PENDING"
    }
    ```

## Security

The API is secured using Spring Security and JWT. To access protected endpoints, you need to include a valid JWT in the `Authorization` header of your request:

```
Authorization: Bearer <your-jwt-token>
```

## Database Migrations

Database migrations are managed using Flyway. Migration scripts are located in `src/main/resources/db/migration`. Flyway automatically applies new migrations on application startup.

## How to Run Tests

To run the test suite, use the following Maven command:
```bash
mvn test
```
This will run all unit and integration tests.
