package com.smartconcierge.config

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object DatabaseConfig {

    private const val ENV_KEY = "KTOR_ENV"
    private const val DEFAULT_ENV = "dev"

    private val config = loadHoconConfig(System.getenv(ENV_KEY) ?: DEFAULT_ENV)

    private val driver = config.getString("app.database.driver")
    private val url = config.getString("app.database.url")
    private val user = config.getString("app.database.user")
    private val password = config.getString("app.database.password")

    fun init() {
        Database.connect(url, driver=driver, user=user, password=password)
        val flyway = Flyway.configure()
            .dataSource(url, user, password)
            .load()

        val migrationsApplied = flyway.migrate()
        println("📦 Flyway migrations applied: ${migrationsApplied.success}")

        // Проверка
        transaction {
            println("✅ Database connected!")
        }
    }

    private fun loadHoconConfig(environment: String): Config {
        return try {
            val configFile = "application-$environment.conf"
            println("Loading configuration from: $configFile")

            val specificConfig = ConfigFactory.load(configFile)

            ConfigFactory.load().withFallback(specificConfig).resolve()
        } catch (e: Exception) {
            println("Failed to load config for environment: $environment, falling back to default")
            ConfigFactory.load("application.conf").resolve()
        }
    }
}