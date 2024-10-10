package com.TrustFlow_Backend_Auth.usecases.task

import com.TrustFlow_Backend_Auth.domain.repositories.TaskRepository
import com.TrustFlow_Backend_Auth.domain.usecases.task.GetCompletedTasks
import com.TrustFlow_Backend_Auth.models.task.Priority
import com.TrustFlow_Backend_Auth.models.task.Status
import com.TrustFlow_Backend_Auth.models.task.Task
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class GetCompletedTasksTest {

    private val taskRepository = mockk<TaskRepository>()
    private val getCompletedTasks = GetCompletedTasks(taskRepository)

    @Test
    fun `should return list of completed tasks`() = runBlocking {
        val userId = 1
        val tasks = listOf(
            Task(id = 3, userId = userId, description = "Completed Task 1", priority = Priority.LOW, status = Status.COMPLETED),
            Task(id = 4, userId = userId, description = "Completed Task 2", priority = Priority.MEDIUM, status = Status.COMPLETED)
        )

        coEvery { taskRepository.getCompletedTasks(userId) } returns tasks

        val result = getCompletedTasks(userId)

        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals(tasks, result)

        coVerify(exactly = 1) { taskRepository.getCompletedTasks(userId) }
    }

    @Test
    fun `should return empty list when no completed tasks exist`() = runBlocking {
        val userId = 1

        coEvery { taskRepository.getCompletedTasks(userId) } returns emptyList()

        val result = getCompletedTasks(userId)

        assertNotNull(result)
        assertTrue(result.isEmpty())

        coVerify(exactly = 1) { taskRepository.getCompletedTasks(userId) }
    }
}
