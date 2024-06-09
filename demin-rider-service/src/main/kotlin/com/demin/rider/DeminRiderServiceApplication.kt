package com.demin.rider

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.demin"])
class DeminRiderServiceApplication

fun main(args: Array<String>) {
    runApplication<DeminRiderServiceApplication>(*args)
}
