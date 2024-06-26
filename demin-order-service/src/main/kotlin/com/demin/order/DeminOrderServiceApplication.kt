package com.demin.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.demin"])
class DeminOrderServiceApplication

fun main(args: Array<String>) {
    runApplication<DeminOrderServiceApplication>(*args)
}
