package com.smartconcierge.dao

import com.smartconcierge.models.UserRole
import com.smartconcierge.models.Users
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object UserDao {

    fun create(email: String, passwordHash: String, name: String, role: UserRole = UserRole.EMPLOYEE): Int {
        return transaction {
            Users.insertAndGetId {
                it[this.email] = email
                it[this.passwordHash] = passwordHash
                it[this.name] = name
                it[this.role] = role
            }.value
        }
    }

    fun findByEmail(email: String): ResultRow? {
        return transaction {
            Users.select(columns = Users.columns).where { Users.email eq email }.singleOrNull()
        }
    }

    fun findById(id: Int): ResultRow? {
        return transaction {
            Users.select(columns = Users.columns).where { Users.id eq id }.singleOrNull()
        }
    }

    fun deleteById(id: Int): Boolean {
        return transaction {
            Users.deleteWhere { Users.id eq id } > 0
        }
    }
}