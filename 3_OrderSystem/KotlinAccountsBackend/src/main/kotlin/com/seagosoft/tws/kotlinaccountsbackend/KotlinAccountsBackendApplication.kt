package com.seagosoft.tws.kotlinaccountsbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import com.seagosoft.tws.config.loadConfig
import java.io.File


// Define some global objects here and initialize them when the application starts
object ReflectionManagerHolder {
    fun initialize() {
        // Get the configuration from config.toml
        val config = loadConfig()
    }
}

@SpringBootApplication
class KotlinAccountsBackendApplication

fun main(args: Array<String>) {

    // Check if the configuration file exists
    if (!File("config.toml").exists()) {
        println("config.toml not found. Application will create a default config.toml file.")

        // Create a default config.toml file
        File("config.toml").writeText("""
            [system]
            sqlitePath = "jdbc:sqlite:database.db"
            sqliteUser = ""
            sqlitePassword = ""
        """.trimIndent())

        // Print a message to the console
        println("config.toml file created. Please edit the file and restart the application.")
    }

    // Initialize the ReflectionManagerHolder object
    ReflectionManagerHolder.initialize()

    // Run the application
    runApplication<KotlinAccountsBackendApplication>(*args)
}
