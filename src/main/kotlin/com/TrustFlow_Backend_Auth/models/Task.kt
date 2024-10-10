package com.TrustFlow_Backend_Auth.models
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

@Serializable
data class Task(
    val id: Int? = null,
    val userId: Int,
    val description: String,
    val priority: Priority,
    val status: Status
)

object Tasks : Table() {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(
        Users.id,
        onDelete = ReferenceOption.CASCADE
    )
    val description = varchar("description", 255)
    val priority = enumerationByName("priority", 10, Priority::class)
    val status = enumerationByName("status", 10, Status::class)

    override val primaryKey = PrimaryKey(id)
}