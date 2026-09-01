# Employee Management System

A RESTful Employee Management System built using Spring Boot.

## Overview

The Employee Management System is a backend REST API for managing employees, departments, and user accounts.

The application provides JWT-based authentication and role-based authorization using two roles:

- ADMIN
- EMPLOYEE

## Features

- Employee CRUD operations
- Department CRUD operations
- User management
- Employee self-profile
- User self-profile
- Employee registration
- Login authentication
- JWT authentication
- Role-based authorization
- BCrypt password encryption
- Request validation
- Global exception handling
- Swagger/OpenAPI documentation
- Unit and integration tests

## Technologies

- Java 21
- Spring Boot 3.3.4
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- MySQL
- Hibernate
- Lombok
- Bean Validation
- Swagger / OpenAPI
- JUnit 5
- Mockito
- Maven

## Security

The application uses JWT-based authentication.

After successful login, the server returns a JWT token.

The token must be sent with protected requests using:

```text
Authorization: Bearer <JWT_TOKEN>
