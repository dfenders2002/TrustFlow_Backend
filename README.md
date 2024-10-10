# Kotlin Backend - Authentication System

## Overview

This is the backend for a basic authentication system, built using Kotlin and the Ktor framework. The backend manages user registration, login, logout, updating personal information, and account deletion.

## Technologies Used

- **Kotlin**
- **Ktor Framework**
- **PostgreSQL** (using pgAdmin for database management)

## Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone <your-repo-link>
   cd <your-repo-folder>
   ```

2. **Database Setup**:
   - Create a PostgreSQL database in pgAdmin.
   - Note down the database name, username, and password.
   - Update the `database.kt` file with your PostgreSQL connection details:
     ```hocon
     database {
       url = "jdbc:postgresql://localhost:5432/<your-database-name>"
       driver = "org.postgresql.Driver"
       user = "<your-db-username>"
       password = "<your-db-password>"
     }
     ```

3. **Build the Project**:
   ```bash
   ./gradlew build
   ```

4. **Run the Project**:
   ```bash
   ./gradlew run
   ```
