package com.smartconcierge.config

import org.flywaydb.core.Flyway
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.Properties

object DatabaseConfig {

    private const val URL = "jdbc:postgresql://localhost:5432/smart_concierge"
    private const val USER = "postgres"
    private const val PASSWORD = "2181"

    fun init() {
        Database.connect(URL, driver="org.postgresql.Driver", user=USER, password=PASSWORD)
        val flyway = Flyway.configure()
            .dataSource(URL, USER, PASSWORD)
            .load()

        val migrationsApplied = flyway.migrate()
        println("📦 Flyway migrations applied: ${migrationsApplied.success}")

        // Проверка
        transaction {
            println("✅ Database connected!")
        }
    }
}