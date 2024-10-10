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

3. **Run Migrations**:
   Make sure to apply migrations (if any) using a migration tool like Flyway or Liquibase, or manually run the SQL scripts located in the `migrations/` folder.

4. **Build the Project**:
   ```bash
   ./gradlew build
   ```

5. **Run the Project**:
   ```bash
   ./gradlew run
   ```

## License

This project is licensed under the MIT License.

---

Now you're all set to run the backend!
=======
# TrustFlow_Backend
>>>>>>> main
