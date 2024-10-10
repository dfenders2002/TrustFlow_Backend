package com.TrustFlow_Backend_Auth.usecases.user

import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.domain.usecases.user.DeleteOtherUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class DeleteOtherUserTest {

    private val userRepository = mockk<UserRepository>()
    private val deleteOtherUser = DeleteOtherUser(userRepository)

    @Test
    fun `should delete other user successfully`() = runBlocking {
        val userId = 2

        coEvery { userRepository.deleteUser(userId) } returns true

        val result = deleteOtherUser(userId)

        assertTrue(result)
        coVerify(exactly = 1) { userRepository.deleteUser(userId) }
    }

    @Test
    fun `should return false when deletion of other user fails`() = runBlocking {
        val userId = 2

        coEvery { userRepository.deleteUser(userId) } returns false

        val result = deleteOtherUser(userId)

        assertFalse(result)
        coVerify(exactly = 1) { userRepository.deleteUser(userId) }
    }
}
