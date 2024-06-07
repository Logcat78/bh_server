plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
    id ("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

group = "org.example.project"
version = "1.0.0"
application {
    mainClass.set("org.example.project.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {

    implementation ("io.ktor:ktor-server-websockets-jvm")
    implementation ("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation ("io.ktor:ktor-server-content-negotiation-jvm")
    implementation ("org.postgresql:postgresql:42.5.1")
    implementation ("com.h2database:h2:2.1.214")
    implementation ("io.ktor:ktor-serialization-gson-jvm")
    implementation ("io.ktor:ktor-server-netty-jvm")
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)

}