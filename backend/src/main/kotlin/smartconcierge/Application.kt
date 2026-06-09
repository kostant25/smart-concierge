package com.smartconcierge

import com.smartconcierge.config.DatabaseConfig
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    DatabaseConfig.init()
}
