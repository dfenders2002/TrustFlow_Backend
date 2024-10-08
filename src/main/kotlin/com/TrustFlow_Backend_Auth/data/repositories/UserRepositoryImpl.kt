package com.TrustFlow_Backend_Auth.data.repositories

import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.models.user.User
import com.TrustFlow_Backend_Auth.models.user.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl : UserRepository {
    override suspend fun addUser(user: User): User? = transaction {
        val insertStatement = Users.insert {
            it[username] = user.username
            it[password] = user.password
            it[email] = user.email
            it[role] = user.role
        }
        insertStatement.resultedValues?.singleOrNull()?.let {
            user.copy(id = it[Users.id])
        }
    }

    override suspend fun findUserByUsername(username: String): User? = transaction {
        Users.selectAll()
            .filter { it[Users.username] == username }
            .map {
                User(
                    id = it[Users.id],
                    username = it[Users.username],
                    password = it[Users.password],
                    email = it[Users.email],
                    role = it[Users.role]
                )
            }
            .singleOrNull()
    }

    override suspend fun updateUser(id: Int, updatedUser: User): Boolean = transaction {
        Users.update({ Users.id eq id }) {
            it[username] = updatedUser.username
            it[password] = updatedUser.password
            it[email] = updatedUser.email
            it[role] = updatedUser.role
        } > 0
    }

    override suspend fun deleteUser(id: Int): Boolean = transaction {
        Users.deleteWhere { Users.id eq id } > 0
    }

    override suspend fun findUserById(id: Int): User? = transaction {
        Users.selectAll() // Start by selecting all users
            .filter { it[Users.id] == id } // Filter to find the user with the specified ID
            .map {
                User(
                    id = it[Users.id],
                    username = it[Users.username],
                    password = it[Users.password],
                    email = it[Users.email],
                    role = it[Users.role]
                )
            }
            .singleOrNull()
    }

    override suspend fun getAllUsers(): List<User> = transaction {
        Users.selectAll().map {
            User(
                id = it[Users.id],
                username = it[Users.username],
                password = it[Users.password],
                email = it[Users.email],
                role = it[Users.role]
            )
        }
    }
}
