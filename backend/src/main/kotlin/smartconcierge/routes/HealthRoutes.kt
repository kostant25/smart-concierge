package com.smartconcierge.routes

import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.healthRoutes() {
    route("/api/health") {
        get {
            call.respond(mapOf("status" to "OK", "timestamp" to "${System.currentTimeMillis()}"))
        }
    }
}