package com.TrustFlow_Backend_Auth.usecases.task

import com.TrustFlow_Backend_Auth.domain.repositories.TaskRepository
import com.TrustFlow_Backend_Auth.domain.usecases.task.DeleteTask
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class DeleteTaskTest {

    private val taskRepository = mockk<TaskRepository>()
    private val deleteTask = DeleteTask(taskRepository)

    @Test
    fun `should delete task successfully`() = runBlocking {
        val taskId = 1

        coEvery { taskRepository.deleteTask(taskId) } returns true

        val result = deleteTask(taskId)

        assertTrue(result)
        coVerify(exactly = 1) { taskRepository.deleteTask(taskId) }
    }

    @Test
    fun `should return false when deletion of task fails`() = runBlocking {
        val taskId = 1

        coEvery { taskRepository.deleteTask(taskId) } returns false

        val result = deleteTask(taskId)

        assertFalse(result)
        coVerify(exactly = 1) { taskRepository.deleteTask(taskId) }
    }
}
