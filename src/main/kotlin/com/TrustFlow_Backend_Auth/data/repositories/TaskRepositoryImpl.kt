package com.TrustFlow_Backend_Auth.data.repositories

import com.TrustFlow_Backend_Auth.domain.repositories.TaskRepository
import com.TrustFlow_Backend_Auth.models.Status
import com.TrustFlow_Backend_Auth.models.Task
import com.TrustFlow_Backend_Auth.models.Tasks
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class TaskRepositoryImpl : TaskRepository {
    override suspend fun createTask(task: Task): Task? = transaction {
        val insertStatement = Tasks.insert {
            it[userId] = task.userId
            it[description] = task.description
            it[priority] = task.priority
            it[status] = task.status
        }
        insertStatement.resultedValues?.singleOrNull()?.let {
            task.copy(id = it[Tasks.id])
        }
    }

    override suspend fun completeTask(id: Int): Boolean = transaction {
        Tasks.update({ Tasks.id eq id }) {
            it[status] = Status.COMPLETED
        } > 0
    }

    override suspend fun deleteTask(id: Int): Boolean = transaction {
        Tasks.deleteWhere { Tasks.id eq id } > 0
    }

    override suspend fun getPendingTasks(userId: Int): List<Task> = transaction {
        Tasks.select { (Tasks.userId eq userId) and (Tasks.status eq Status.PENDING) }
            .map {
                Task(
                    id = it[Tasks.id],
                    userId = it[Tasks.userId],
                    description = it[Tasks.description],
                    priority = it[Tasks.priority],
                    status = it[Tasks.status]
                )
            }
    }

    override suspend fun getCompletedTasks(userId: Int): List<Task> = transaction {
        Tasks.select { (Tasks.userId eq userId) and (Tasks.status eq Status.COMPLETED) }
            .map {
                Task(
                    id = it[Tasks.id],
                    userId = it[Tasks.userId],
                    description = it[Tasks.description],
                    priority = it[Tasks.priority],
                    status = it[Tasks.status]
                )
            }
    }

    override suspend fun findTaskById(id: Int): Task? = transaction {
        Tasks.select { Tasks.id eq id }
            .map {
                Task(
                    id = it[Tasks.id],
                    userId = it[Tasks.userId],
                    description = it[Tasks.description],
                    priority = it[Tasks.priority],
                    status = it[Tasks.status]
                )
            }
            .singleOrNull()
    }
}
