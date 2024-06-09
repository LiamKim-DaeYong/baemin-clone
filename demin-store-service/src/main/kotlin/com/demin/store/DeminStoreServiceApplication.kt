package com.demin.store

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.demin"])
class DeminStoreServiceApplication

fun main(args: Array<String>) {
    runApplication<DeminStoreServiceApplication>(*args)
}
