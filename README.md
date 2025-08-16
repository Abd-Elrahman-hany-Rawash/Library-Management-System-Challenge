Library Management System - README



1. Project Overview

The Library Management System (LMS) is a professional-grade Spring Boot application designed to streamline library operations, including managing books, authors, categories, and user lending activities. The system offers a secure, RESTful API with full CRUD capabilities and role-based access control. Its primary objective is to empower library administrators and staff to manage resources efficiently while providing members with a seamless borrowing experience.

2. System Architecture

The LMS adopts a layered architecture, ensuring separation of concerns, maintainability, and scalability:

Controller Layer: Handles HTTP requests and returns structured responses.

Service Layer: Encapsulates business logic, transaction management, and validation.

Repository Layer: Interfaces with the database using Spring Data JPA.

Entity Layer: Maps Java objects to relational database tables via JPA annotations.



2.1 Technologies & Libraries

Java 17 – Leveraging modern language features and LTS support.

Spring Boot – Accelerates application setup and configuration.

Spring Data JPA + Hibernate – Object-relational mapping for seamless database interaction.

Spring Security – Provides authentication and fine-grained role-based authorization.

MySQL – Reliable relational database.

Postman – API testing and validation.

Lombok – Reduces boilerplate code with annotations.
