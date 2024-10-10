package com.TrustFlow_Backend_Auth.usecases.user

import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.domain.usecases.user.GetAllUsers
import com.TrustFlow_Backend_Auth.models.user.Role
import com.TrustFlow_Backend_Auth.models.user.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class GetAllUsersTest {

    private val userRepository = mockk<UserRepository>()
    private val getAllUsers = GetAllUsers(userRepository)

    @Test
    fun `should return list of all users`() = runBlocking {
        val users = listOf(
            User(id = 1, username = "user1", password = "pass1", email = "user1@example.com", role = Role.USER),
            User(id = 2, username = "user2", password = "pass2", email = "user2@example.com", role = Role.ADMIN)
        )

        coEvery { userRepository.getAllUsers() } returns users

        val result = getAllUsers()

        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals(users, result)

        coVerify(exactly = 1) { userRepository.getAllUsers() }
    }

    @Test
    fun `should return empty list when no users exist`() = runBlocking {
        coEvery { userRepository.getAllUsers() } returns emptyList()

        val result = getAllUsers()

        assertNotNull(result)
        assertTrue(result.isEmpty())

        coVerify(exactly = 1) { userRepository.getAllUsers() }
    }
}
