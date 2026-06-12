package com.smartconcierge.models

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.time.Clock

enum class UserRole {
    ADMIN, EMPLOYEE
}

object Users : IntIdTable("users") {
    val email = varchar("email", 255)
    val passwordHash = varchar("password_hash", 255)
    val name = varchar("name", 255)
    val role = enumeration("role", UserRole::class)
    val createdAt = datetime(name = "created_at").default(Clock.System.now().toLocalDateTime(TimeZone.UTC))

    init {
        index(true, email)
    }
}
