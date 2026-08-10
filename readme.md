# 🐳 DockerL - Spring Boot & Docker Learning Sandbox

A full-stack, layered Spring Boot RESTful application built as a hands-on playground to master backend architecture, custom exception handling, and Docker containerization.

---

## 📸 Overview & Key Features

* **Layered Architecture:** Strict separation of concerns via Controller, Service (`ServiceImpl`), Repository, Exception, and DTO layers.
* **RESTful API:** Full CRUD operations for managing student records.
* **Global Exception Handling:** Centralized error responses (`@ControllerAdvice`) handling custom exceptions like `DuplicateEmailException` and `StudentNotFoundException`.
* **Gemini-Inspired Dark UI:** Clean, responsive, glassmorphic frontend built with HTML, CSS, and Vanilla JavaScript (`fetch` API).
* **In-Memory Database:** Powered by H2 Database with embedded web console access (`/h2-console`).
* **Containerized Deployment:** Multi-stage `Dockerfile` and `compose.yaml` (Docker Compose) setup for lightweight, production-ready packaging.

---

## 🛠️ Tech Stack

* **Backend:** Java 17+, Spring Boot 3, Spring Data JPA
* **Database:** H2 Database (In-Memory)
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
├── compose.yaml                     # Docker Compose Configuration
├── Dockerfile                       # Multi-stage Docker build script
├── mvnw / mvnw.cmd                  # Maven Executable Wrappers
└── pom.xml                          # Dependencies & Build Config
