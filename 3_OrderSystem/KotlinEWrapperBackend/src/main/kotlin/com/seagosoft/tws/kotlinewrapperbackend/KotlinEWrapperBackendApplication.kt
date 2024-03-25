package com.seagosoft.tws.kotlinewrapperbackend

import com.seagosoft.tws.config.loadConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


// Import the TwsWrapper class here
import com.seagosoft.tws.wrapper.TwsWrapper_V1
import java.io.File

// Define some global objects here and initialize them when the application starts
object ReflectionManagerHolder {
    lateinit var twsService: TwsWrapper_V1 // Assuming ReflectionManager is your class
    fun initialize() {

        // Get the configuration from config.toml
        val config = loadConfig()

        // Initialize the wrapper object
        twsService = TwsWrapper_V1(config.twsHost, config.twsPort, config.zeroPort)
    }
}

@SpringBootApplication
class KotlinEWrapperBackendApplication

fun main(args: Array<String>) {

    // Check if the configuration file exists
    if (!File("config.toml").exists()) {
        println("config.toml not found. Application will create a default config.toml file.")

        // Create a default config.toml file
        File("config.toml").writeText("""
            [system]
            twsHost = "127.0.0.1"
            twsPort = 7497
            zeroPort = 7496
        """.trimIndent())

        // Print a message to the console
        println("config.toml file created. Please edit the file and restart the application.")
    }

    // Initialize the ReflectionManagerHolder object
    ReflectionManagerHolder.initialize()

    // Run the Spring Boot application
    runApplication<KotlinEWrapperBackendApplication>(*args)
}
