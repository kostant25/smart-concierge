package com.smartconcierge.dao

import com.smartconcierge.models.Roles
import com.smartconcierge.models.Users
import com.smartconcierge.routes.RegisterResponse
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object UserDao {

    fun create(email: String, passwordHash: String, name: String, roleId: Int = 2): Int {
        return transaction {
            Users.insertAndGetId {
                it[this.email] = email
                it[this.passwordHash] = passwordHash
                it[this.name] = name
                it[this.roleId] = roleId
            }.value
        }
    }

    fun getAll(): List<RegisterResponse> {
        return transaction {
            (Users innerJoin Roles)
                .select(Users.id, Users.email, Users.name, Roles.name)
                .map { row ->
                    RegisterResponse(
                        id = row[Users.id].value,
                        email = row[Users.email],
                        name = row[Users.name],
                        role = row[Roles.name]
                    )
                }
        }
    }

    fun findByEmail(email: String): RegisterResponse? {
        return findUser { Users.email eq email }
    }

    fun findById(id: Int): RegisterResponse? {
        return findUser { Users.id eq id }
    }

    fun deleteById(id: Int): Boolean {
        return transaction {
            Users.deleteWhere { Users.id eq id } > 0
        }
    }

    private fun findUser(where: () -> Op<Boolean>): RegisterResponse? {
        return transaction {
            (Users innerJoin Roles)
                .select(Users.id, Users.email, Users.name, Roles.name)
                .where(where)
                .map { row ->
                    RegisterResponse(
                        id = row[Users.id].value,
                        email = row[Users.email],
                        name = row[Users.name],
                        role = row[Roles.name]
                    )
                }.singleOrNull()
        }
    }

}