package com.smartconcierge.routes

import com.smartconcierge.dao.UserDao
import com.smartconcierge.plugins.isValidEmail
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.serialization.Serializable

fun Route.authRoutes() {
    route("/api/auth") {
        post("/register") {
            val body = call.receive<RegisterRequest>()

            if (!body.email.isValidEmail()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid email format")
                return@post
            }

            if (UserDao.findByEmail(body.email) != null) {
                call.respond(HttpStatusCode.Conflict, "Email already exists")
                return@post
            }

            val newUserId = try {
                UserDao.create(
                    body.email,
                    body.password,
                    body.name
                )
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Failed to create user")
                return@post
            }

            val user = UserDao.findById(newUserId)

            if (user == null) {
                call.respond(HttpStatusCode.InternalServerError, "User created but not found")
                return@post
            }

            call.respond(HttpStatusCode.Created, user)

        }

        get("/users") {
            val users = UserDao.getAll()

            call.respond(HttpStatusCode.OK, users)
        }
    }
}

@Serializable
data class RegisterResponse(
    val id: Int,
    val email: String,
    val name: String,
    val role: String
)

@Serializable
data class RegisterRequest(val email: String, val password: String, val name: String)