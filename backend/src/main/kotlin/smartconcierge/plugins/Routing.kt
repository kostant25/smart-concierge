package com.smartconcierge.plugins

import com.smartconcierge.routes.authRoutes
import com.smartconcierge.routes.healthRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        healthRoutes()
        authRoutes()
    }
}