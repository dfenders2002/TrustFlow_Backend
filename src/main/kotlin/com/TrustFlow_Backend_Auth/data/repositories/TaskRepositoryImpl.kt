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

    suspend fun getTasksByStatus(userId: Int, status: Status): List<Task> = transaction {
        Tasks.selectAll()
            .mapNotNull {
                if ((it[Tasks.userId] == userId) && (it[Tasks.status] == status)) {
                    toTask(it)
                } else {
                    null
                }
            }
    }

    private fun toTask(row: ResultRow): Task {
        return Task(
            id = row[Tasks.id],
            userId = row[Tasks.userId],
            description = row[Tasks.description],
            priority = row[Tasks.priority],
            status = row[Tasks.status]
        )
    }

    override suspend fun getPendingTasks(userId: Int): List<Task> {
        return getTasksByStatus(userId, Status.PENDING)
    }

    override suspend fun getCompletedTasks(userId: Int): List<Task> {
        return getTasksByStatus(userId, Status.COMPLETED)
    }

    override suspend fun findTaskById(id: Int): Task? = transaction {
        Tasks.selectAll()
            .mapNotNull {
                if (it[Tasks.id] == id) {
                    toTask(it)
                } else {
                    null
                }
            }.singleOrNull()
    }
}
