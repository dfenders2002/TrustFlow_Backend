package com.TrustFlow_Backend_Auth.plugins

import com.TrustFlow_Backend_Auth.models.Users
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create

fun Application.configureDatabases() {
    val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/postgres",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "WelkomDaan",
    )
    transaction(database) {
        create(Users)
    }
}
