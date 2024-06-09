package com.demin.settlement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.demin"])
class DeminSettlementServiceApplication

fun main(args: Array<String>) {
    runApplication<DeminSettlementServiceApplication>(*args)
}
