package com.demin.relay

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.demin"])
class DeminRelayServiceApplication

fun main(args: Array<String>) {
    runApplication<DeminRelayServiceApplication>(*args)
}
