# 🐳 DockerL - Spring Boot, Docker & Redis Learning Sandbox

<div align="center">

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring%20boot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

</div>

A full-stack, layered Spring Boot RESTful application built as a hands-on playground to master backend architecture, custom exception handling, Docker containerization, and Redis caching.

---

## 📸 Overview & Key Features

* **Layered Architecture:** Strict separation of concerns via Controller, Service (`ServiceImpl`), Repository, Exception, and DTO layers.
* **RESTful API:** Full CRUD operations for managing student records.
* **High-Performance Caching:** Integrated **Redis** to cache frequent database queries, drastically improving response times.
* **Automated Data Seeding:** A custom initialization configuration automatically populates the database with dummy records on startup.
* **Global Exception Handling:** Centralized error responses (`@ControllerAdvice`) handling custom exceptions like `DuplicateEmailException` and `StudentNotFoundException`.
* **Gemini-Inspired Dark UI:** Clean, responsive, glassmorphic frontend built with HTML, CSS, and Vanilla JavaScript (`fetch` API).
* **In-Memory Database:** Powered by H2 Database with embedded web console access (`/h2-console`).
* **Containerized Deployment:** Multi-stage `Dockerfile` and `compose.yaml` (Docker Compose) setup for lightweight, production-ready packaging of both the Spring Boot app and the Redis instance.

---

## 🛠️ Tech Stack

* **Backend:** Java 17+, Spring Boot 3, Spring Data JPA
* **Database & Caching:** H2 Database (In-Memory), Redis
* **Frontend:** HTML5, Modern CSS (Glassmorphism), Vanilla JavaScript
* **Containerization:** Docker, Docker Compose
* **Build Tool:** Maven

---

## 📂 Project Structure

```text
DockerL
├── src
│   ├── main
│   │   ├── java/com/example/docker/DockerL
│   │   │   ├── config/              # Configuration & Initialization
│   │   │   │   ├── DataSeeder.java
│   │   │   │   └── RedisConfig.java
│   │   │   ├── controller/          # REST API & Page View Controllers
│   │   │   │   ├── PageController.java
│   │   │   │   └── StudentController.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── StudentRequestDto.java
│   │   │   │   └── StudentResponseDto.java
│   │   │   ├── entity/              # JPA Entities
│   │   │   │   └── Student.java
│   │   │   ├── exception/           # Custom Exceptions & Global Handler
│   │   │   │   ├── DuplicateEmailException.java
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── StudentNotFoundException.java
│   │   │   ├── repository/          # Spring Data JPA Repository Interfaces
│   │   │   │   └── StudentRepository.java
│   │   │   ├── service/             # Service Layer Interfaces & Implementations
│   │   │   │   ├── StudentServices.java
│   │   │   │   └── StudentServicesImpl.java
│   │   │   └── DockerLApplication.java
│   │   └── resources
│   │       ├── static/              # CSS Styles & Client Scripts
│   │       │   ├── css/style.css
│   │       │   └── js/script.js
│   │       ├── templates/           # HTML Templates
│   │       │   └── index.html
│   │       └── application.properties
│   └── test/
├── .dockerignore                    # Ignored files for Docker builds
├── compose.yaml                     # Docker Compose Config (Spring Boot + Redis)
├── Dockerfile                       # Multi-stage Docker build script
├── mvnw / mvnw.cmd                  # Maven Executable Wrappers
└── pom.xml                          # Dependencies & Build Config
