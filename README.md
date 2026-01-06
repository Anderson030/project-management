# 🚀 Project & Task Management System

A professional, full-stack application built to demonstrate **Clean Architecture (Hexagonal)**, **Spring Boot 3**, and **React**.

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green.svg)
![React](https://img.shields.io/badge/React-18.0-blue.svg)

## 📋 Features

- **Hexagonal Architecture**: Strict separation of concerns (Domain, Application, Infrastructure, Presentation).
- **Secure Authentication**: JWT-based security with BCrypt password hashing.
- **Role-Based Access**: Users can only manage their own projects and tasks.
- **RESTful API**: Fully documented endpoints for managing Projects and Tasks.
- **Modern UI**: Responsive React frontend with a premium dark mode aesthetic.
- **Dockerized**: specific `Dockerfile` and `docker-compose.yml` for easy deployment.

## 🛠️ Tech Stack

### Backend
- **Core**: Java 17, Spring Boot 3
- **Security**: Spring Security 6, JWT (JSON Web Tokens)
- **Persistence**: Spring Data JPA, Hibernate, PostgreSQL
- **Testing**: JUnit 5, Mockito
- **Docs**: SpringDoc OpenAPI (Swagger)

### Frontend
- **Framework**: React 18
- **Styling**: Vanilla CSS (Glassmorphism design)
- **Routing**: React Router 6
- **HTTP Client**: Axios with Interceptors

### DevOps
- **Containerization**: Docker, Docker Compose
- **Database**: PostgreSQL (Supabase compatible)

---

## ⚡ Quick Start (Docker)

The easiest way to run the application is using Docker Compose.

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/project-management.git
   cd project-management
   ```

2. **Run with Docker Compose**
   ```bash
   docker-compose up --build
   ```

3. **Access the Application**
   - **Frontend**: [http://localhost:3000](http://localhost:3000)
   - **Backend API**: [http://localhost:8080/api](http://localhost:8080/api)
   - **Swagger Docs**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🔧 Manual Setup (Local Development)

### Backend

1. Navigate to the backend directory (root).
2. Configure your database in `src/main/resources/application.properties` (if not using the default Docker config).
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

### Frontend

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm start
   ```

---

## 🔑 Default Credentials

The system includes an automatic seeder that creates an admin user on the first run.

- **Username**: `admin`
- **Password**: `123`

> **Note**: You can also register new users via the `POST /api/auth/register` endpoint or using the generic registration logic if implemented in the frontend.

---

## 🏗️ Architecture Overview

This project strictly follows **Hexagonal Architecture (Ports & Adapters)**:

```
src/main/java/com/anderson/dev/projectmanagement
├── application          # Business Logic (Use Cases & Services)
│   ├── port             # Input/Output Interfaces (Ports)
│   └── service          # Implementation of Use Cases
├── domain               # Core Business Rules (Entities, Exceptions)
├── infrastructure       # Frameworks & Drivers
│   ├── persistence      # Database Adapters (JPA)
│   ├── security         # Auth & JWT Config
│   └── config           # App Configuration
└── presentation         # Entry Points (REST Controllers)
```

- **Domain Layer**: completely isolated from frameworks (no Spring annotations).
- **Application Layer**: orchestrates logic using Ports.
- **Infrastructure Layer**: implements Output Ports (Repositories, External Services).
- **Presentation Layer**: implements Input Ports (REST API).

## 🧪 Testing

Run strict unit tests covering core business rules:

```bash
./mvnw test
```

Includes coverage for:
- `ActivateProjectUseCase`
- `CompleteTaskUseCase`
- Authorization boundaries (Owner vs Non-Owner)
