package com.TrustFlow_Backend_Auth.usecases.task

import com.TrustFlow_Backend_Auth.domain.repositories.TaskRepository
import com.TrustFlow_Backend_Auth.domain.usecases.task.CreateTask
import com.TrustFlow_Backend_Auth.models.task.Priority
import com.TrustFlow_Backend_Auth.models.task.Status
import com.TrustFlow_Backend_Auth.models.task.Task
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class CreateTaskTest {

    private val taskRepository = mockk<TaskRepository>()
    private val createTask = CreateTask(taskRepository)

    @Test
    fun `should create task successfully`() = runBlocking {
        val task = Task(
            userId = 1,
            description = "Test Task",
            priority = Priority.HIGH,
            status = Status.PENDING
        )
        val savedTask = task.copy(id = 1)

        coEvery { taskRepository.createTask(task) } returns savedTask

        val result = createTask(task)

        assertNotNull(result)
        assertEquals(savedTask, result)

        coVerify(exactly = 1) { taskRepository.createTask(task) }
    }

    @Test
    fun `should return null when task creation fails`() = runBlocking {
        val task = Task(
            userId = 1,
            description = "Test Task",
            priority = Priority.HIGH,
            status = Status.PENDING
        )

        coEvery { taskRepository.createTask(task) } returns null

        val result = createTask(task)

        assertNull(result)

        coVerify(exactly = 1) { taskRepository.createTask(task) }
    }
}
