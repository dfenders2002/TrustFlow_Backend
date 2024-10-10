package com.TrustFlow_Backend_Auth.routes

import com.TrustFlow_Backend_Auth.data.repositories.TaskRepositoryImpl
import com.TrustFlow_Backend_Auth.domain.usecases.task.CompleteTask
import com.TrustFlow_Backend_Auth.domain.usecases.task.CreateTask
import com.TrustFlow_Backend_Auth.domain.usecases.task.DeleteTask
import com.TrustFlow_Backend_Auth.domain.usecases.task.GetCompletedTasks
import com.TrustFlow_Backend_Auth.domain.usecases.task.GetPendingTasks
import com.TrustFlow_Backend_Auth.models.*
import com.TrustFlow_Backend_Auth.sessions.UserSession
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.taskRoutes() {
    // Initialize repository and use cases
    val taskRepository = TaskRepositoryImpl()
    val createTask = CreateTask(taskRepository)
    val completeTask = CompleteTask(taskRepository)
    val deleteTask = DeleteTask(taskRepository)
    val getPendingTasks = GetPendingTasks(taskRepository)
    val getCompletedTasks = GetCompletedTasks(taskRepository)

    post("/tasks") {
        val session = call.sessions.get<UserSession>()
        if (session == null) {
            call.respond(mapOf("status" to "Unauthorized"))
            return@post
        }

        val taskRequest = call.receive<CreateTaskRequest>()
        val task = Task(
            userId = session.userId,
            description = taskRequest.description,
            priority = taskRequest.priority,
            status = Status.PENDING
        )
        val createdTask = createTask(task)
        if (createdTask != null) {
            call.respond(TaskResponse(status = "Task created", task = createdTask))
        } else {
            call.respond(mapOf("status" to "Task creation failed"))
        }
    }

    put("/tasks/{id}/complete") {
        val session = call.sessions.get<UserSession>()
        if (session == null) {
            call.respond(mapOf("status" to "Unauthorized"))
            return@put
        }

        val idParam = call.parameters["id"]?.toIntOrNull()
        if (idParam == null) {
            call.respond(mapOf("status" to "Invalid task ID"))
            return@put
        }

        val task = taskRepository.findTaskById(idParam)
        if (task == null || task.userId != session.userId) {
            call.respond(mapOf("status" to "Task not found or unauthorized"))
            return@put
        }

        val success = completeTask(idParam)
        if (success) {
            call.respond(mapOf("status" to "Task completed"))
        } else {
            call.respond(mapOf("status" to "Failed to complete task"))
        }
    }

    delete("/tasks/{id}") {
        val session = call.sessions.get<UserSession>()
        if (session == null) {
            call.respond(mapOf("status" to "Unauthorized"))
            return@delete
        }

        val idParam = call.parameters["id"]?.toIntOrNull()
        if (idParam == null) {
            call.respond(mapOf("status" to "Invalid task ID"))
            return@delete
        }

        val task = taskRepository.findTaskById(idParam)
        if (task == null || task.userId != session.userId) {
            call.respond(mapOf("status" to "Task not found or unauthorized"))
            return@delete
        }

        val success = deleteTask(idParam)
        if (success) {
            call.respond(mapOf("status" to "Task deleted"))
        } else {
            call.respond(mapOf("status" to "Failed to delete task"))
        }
    }

    get("/tasks/pending") {
        val session = call.sessions.get<UserSession>()
        if (session == null) {
            call.respond(mapOf("status" to "Unauthorized"))
            return@get
        }

        val tasks = getPendingTasks(session.userId)
        call.respond(TasksResponse(status = "success", tasks = tasks))
    }

    get("/tasks/completed") {
        val session = call.sessions.get<UserSession>()
        if (session == null) {
            call.respond(mapOf("status" to "Unauthorized"))
            return@get
        }

        val tasks = getCompletedTasks(session.userId)
        call.respond(TasksResponse(status = "success", tasks = tasks))
    }
}