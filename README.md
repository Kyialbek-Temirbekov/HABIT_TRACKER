# Habit Tracker

## Description  
Habit Tracker is a Spring Boot application designed to help users build and maintain good habits. It provides features for tracking daily, weekly, or monthly habits, setting goals, monitoring progress, and receiving achievements. The application includes user authentication, habit management, reminders, and progress tracking functionality.

Key Features:
- User registration and authentication (JWT & OAuth2)
- Habit creation with customizable frequency and time of day
- Progress tracking with visual indicators
- Achievement system for motivation
- Email notifications and reminders
- REST API with comprehensive documentation (Swagger/OpenAPI)

## Technologies Used

### Backend
- **Spring Boot 3.1.2**
- **Spring Security** (JWT & OAuth2)
- **Spring Data JPA**
- **PostgreSQL** (Primary database)
- **H2 Database** (For testing)
- **ModelMapper** (For DTO conversions)
- **Lombok** (For boilerplate code reduction)
- **SpringDoc OpenAPI** (API documentation)
- **JJWT** (JWT token handling)

### Infrastructure
- **Maven** (Build tool)
- **Git** (Version control)

## Usage

The application provides a RESTful API that can be consumed by any HTTP client. API documentation is available via Swagger UI at `/swagger-ui.html` when the application is running.

### Authentication
- JWT authentication for regular users
- OAuth2 integration with Google
- Role-based authorization (USER, COPYWRITER, MANAGER, ADMIN)

### API Endpoints
- User management (`/person`)
- Habit tracking (`/habit`)
- Shared habits library (`/sharedHabit`)
- Achievements (`/achievement`)
- Contact form (`/contact`)
- Admin permissions (`/permission`)

To run the application locally, you'll need:
1. Java 17
2. PostgreSQL database
3. Configured application.properties with your database credentials
4. Optional: SMTP server credentials for email functionality

The application can be started using:
```
./mvnw spring-boot:run
```
