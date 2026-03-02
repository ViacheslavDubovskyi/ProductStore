# Product Store

A RESTful Spring Boot application with a layered architecture that provides full CRUD operations for managing products and orders. The system uses PostgreSQL for persistence and is fully containerized using Docker.  

## 📌 Features

- CRUD operations for products, orders and items  
- Add and remove products inside orders  
- PostgreSQL persistence with Hibernate  
- Environment-based configuration  
- Docker & Docker Compose support  
- Layered architecture (Controller–Service–Repository)  
- DTO ↔ Entity mapping  
- Unit, Web, and Integration tests (JUnit 5 & Mockito)  

## 🏗 Architecture

The project follows a classic layered architecture:

- Controller: Handles HTTP requests and responses:
- Service: Contains business logic and orchestrates operations between controllers and repositories.
- Repository: Provides database access using Spring Data JPA.
- Domain (Entities): Represents the database model mapped via Hibernate.
- DTO + Mapper: Separates API models from persistence models for clean design.

This architecture improves maintainability, scalability, and testability.

## 🛠 Technologies

- Java 17  
- Spring Boot  
- Spring Web  
- Spring Data JPA (Hibernate)  
- PostgreSQL  
- Docker  
- Docker Compose  
- JUnit 5  
- Mockito  
- Maven

## ⚙️ Build & Run

### 1️⃣ Build the project

`mvn clean package `

2️⃣ Run with Docker

`docker compose up --build`

The application will start at:

http://localhost:8080

PostgreSQL runs inside a Docker container.

To stop containers:

`docker compose down`

## 🧪 Testing

The project includes:

- Unit tests (Service layer using Mockito)
- Web layer tests (@WebMvcTest, MockMvc)
- Integration tests (@SpringBootTest, transactional)

Run tests with:

`mvn test`
