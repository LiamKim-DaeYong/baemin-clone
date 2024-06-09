package com.demin.point

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.demin"])
class DeminPointServiceApplication

fun main(args: Array<String>) {
    runApplication<DeminPointServiceApplication>(*args)
}
