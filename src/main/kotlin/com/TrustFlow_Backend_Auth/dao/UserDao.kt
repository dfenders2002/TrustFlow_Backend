package com.TrustFlow_Backend_Auth.dao

import com.TrustFlow_Backend_Auth.models.User
import com.TrustFlow_Backend_Auth.models.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserDao {
    fun addUser(user: User): User? = transaction {
        val insertStatement = Users.insert {
            it[username] = user.username
            it[password] = user.password
            it[email] = user.email
        }
        insertStatement.resultedValues?.singleOrNull()?.let {
            user.copy(id = it[Users.id])
        }
    }

    fun findUserByUsername(username: String): User? = transaction {
        Users.selectAll()
            .andWhere { Users.username eq username }
            .map { User(it[Users.id], it[Users.username], it[Users.password], it[Users.email]) }
            .singleOrNull()
    }


    fun updateUser(id: Int, updatedUser: User): Boolean = transaction {
        Users.update({ Users.id eq id }) {
            it[username] = updatedUser.username
            it[password] = updatedUser.password
            it[email] = updatedUser.email
        } > 0
    }

//    fun deleteUser(id: Int): Boolean = transaction {
//        Users.deleteWhere { Users.id eq id } > 0
//    }
}