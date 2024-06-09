package com.demin.payment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.demin"])
class DeminPaymentServiceApplication

fun main(args: Array<String>) {
    runApplication<DeminPaymentServiceApplication>(*args)
}
