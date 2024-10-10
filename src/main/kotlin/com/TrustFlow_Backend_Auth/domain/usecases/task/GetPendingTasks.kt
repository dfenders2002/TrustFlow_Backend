package com.TrustFlow_Backend_Auth.domain.usecases.task

import com.TrustFlow_Backend_Auth.domain.repositories.TaskRepository
import com.TrustFlow_Backend_Auth.models.Task

class GetPendingTasks(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(userId: Int): List<Task> {
        return taskRepository.getPendingTasks(userId)
    }
}