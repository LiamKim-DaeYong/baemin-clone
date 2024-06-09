package com.demin.menu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.demin"])
class DeminMenuServiceApplication

fun main(args: Array<String>) {
    runApplication<DeminMenuServiceApplication>(*args)
}
