package com.TrustFlow_Backend_Auth.usecases.task

import com.TrustFlow_Backend_Auth.domain.repositories.TaskRepository
import com.TrustFlow_Backend_Auth.domain.usecases.task.CompleteTask
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class CompleteTaskTest {

    private val taskRepository = mockk<TaskRepository>()
    private val completeTask = CompleteTask(taskRepository)

    @Test
    fun `should complete task successfully`() = runBlocking {
        val taskId = 1

        coEvery { taskRepository.completeTask(taskId) } returns true

        val result = completeTask(taskId)

        assertTrue(result)
        coVerify(exactly = 1) { taskRepository.completeTask(taskId) }
    }

    @Test
    fun `should return false when completing task fails`() = runBlocking {
        val taskId = 1

        coEvery { taskRepository.completeTask(taskId) } returns false

        val result = completeTask(taskId)

        assertFalse(result)
        coVerify(exactly = 1) { taskRepository.completeTask(taskId) }
    }
}
