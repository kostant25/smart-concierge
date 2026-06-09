package com.smartconcierge

import com.smartconcierge.config.DatabaseConfig
import com.smartconcierge.plugins.configureRouting
import com.smartconcierge.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    DatabaseConfig.init()
}
