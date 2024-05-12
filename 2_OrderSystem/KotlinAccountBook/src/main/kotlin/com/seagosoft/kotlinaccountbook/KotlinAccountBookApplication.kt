package com.seagosoft.kotlinaccountbook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinAccountBookApplication

fun main(args: Array<String>) {
    runApplication<KotlinAccountBookApplication>(*args)
}
