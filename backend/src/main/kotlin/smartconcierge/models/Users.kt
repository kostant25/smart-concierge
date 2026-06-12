package com.smartconcierge.models

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.time.Clock

object Users : IntIdTable("users") {
    val email = varchar("email", 255)
    val passwordHash = varchar("password_hash", 255)
    val name = varchar("name", 255)
    val roleId = reference("role_id", Roles)
    val createdAt = datetime(name = "created_at").default(Clock.System.now().toLocalDateTime(TimeZone.UTC))

    init {
        index(true, email)
    }
}

object Roles: IntIdTable("roles") {
    val name = varchar("name", 255)
}
