package com.TrustFlow_Backend_Auth.domain.usecases.task

import com.TrustFlow_Backend_Auth.domain.repositories.TaskRepository

class CompleteTask(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(id: Int): Boolean {
        return taskRepository.completeTask(id)
    }
}