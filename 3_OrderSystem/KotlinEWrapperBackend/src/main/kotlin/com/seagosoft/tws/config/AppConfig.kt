package com.seagosoft.tws.config

import com.moandjiezana.toml.Toml
import java.io.File
import java.io.FileNotFoundException


data class SystemConfig (
        val twsHost: String,
        val twsPort: Int,
        val zeroPort: Int,
)

fun loadConfig(): SystemConfig {
    // Load the config file
    val file = File("config.toml")
    if (!file.exists()) {
        throw FileNotFoundException("config.toml not found.")
    }

    // Read the config file
    val toml = Toml().read(file)

    // Get the system configuration
    val systemConfig = toml.getTable("system")
    return SystemConfig(
            twsHost = systemConfig.getString("twsHost"),
            twsPort = systemConfig.getLong("twsPort").toInt(),
            zeroPort = systemConfig.getLong("zeroPort").toInt(),
    )
}