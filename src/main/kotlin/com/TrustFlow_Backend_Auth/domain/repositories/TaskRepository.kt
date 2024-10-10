package com.TrustFlow_Backend_Auth.domain.repositories

import com.TrustFlow_Backend_Auth.models.task.Task

interface TaskRepository {
    suspend fun createTask(task: Task): Task?
    suspend fun completeTask(id: Int): Boolean
    suspend fun deleteTask(id: Int): Boolean
    suspend fun getPendingTasks(userId: Int): List<Task>
    suspend fun getCompletedTasks(userId: Int): List<Task>
    suspend fun findTaskById(id: Int): Task?
}
