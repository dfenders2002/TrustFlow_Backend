package com.TrustFlow_Backend_Auth.plugins

import com.TrustFlow_Backend_Auth.models.Tasks
import com.TrustFlow_Backend_Auth.models.Users
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/postgres",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "WelkomDaan",
    )
    transaction(database) {
        SchemaUtils.createMissingTablesAndColumns(Users, Tasks)
    }
}
