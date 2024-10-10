package com.TrustFlow_Backend_Auth.domain.usecases.task

import com.TrustFlow_Backend_Auth.domain.repositories.TaskRepository
import com.TrustFlow_Backend_Auth.models.Task

class CreateTask(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task): Task? {
        return taskRepository.createTask(task)
    }
}