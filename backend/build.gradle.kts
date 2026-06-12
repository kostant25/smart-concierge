
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
}

group = "com"
version = "1.0.0-SNAPSHOT"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
    jvmToolchain(21)
}
dependencies {
    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(ktorLibs.server.auth)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)
    implementation(libs.exposed.core)
    implementation(libs.exposed.r2dbc)
    implementation(libs.logback.classic)
    implementation(libs.postgresql)
    implementation("org.flywaydb:flyway-core:9.22.3")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:1.3.0")

    testImplementation(kotlin("test"))
    testImplementation(ktorLibs.server.testHost)
}
