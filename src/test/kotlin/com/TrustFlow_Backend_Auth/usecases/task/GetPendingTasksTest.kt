package com.TrustFlow_Backend_Auth.usecases.task

import com.TrustFlow_Backend_Auth.domain.repositories.TaskRepository
import com.TrustFlow_Backend_Auth.domain.usecases.task.GetPendingTasks
import com.TrustFlow_Backend_Auth.models.task.Priority
import com.TrustFlow_Backend_Auth.models.task.Status
import com.TrustFlow_Backend_Auth.models.task.Task
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GetPendingTasksTest {

    private val taskRepository = mockk<TaskRepository>()
    private val getPendingTasks = GetPendingTasks(taskRepository)

    @Test
    fun `should return list of pending tasks`() = runBlocking {
        val userId = 1
        val tasks = listOf(
            Task(id = 1, userId = userId, description = "Pending Task 1", priority = Priority.HIGH, status = Status.PENDING),
            Task(id = 2, userId = userId, description = "Pending Task 2", priority = Priority.MEDIUM, status = Status.PENDING)
        )

        coEvery { taskRepository.getPendingTasks(userId) } returns tasks

        val result = getPendingTasks(userId)

        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals(tasks, result)

        coVerify(exactly = 1) { taskRepository.getPendingTasks(userId) }
    }

    @Test
    fun `should return empty list when no pending tasks exist`() = runBlocking {
        val userId = 1

        coEvery { taskRepository.getPendingTasks(userId) } returns emptyList()

        val result = getPendingTasks(userId)

        assertNotNull(result)
        assertTrue(result.isEmpty())

        coVerify(exactly = 1) { taskRepository.getPendingTasks(userId) }
    }
}
