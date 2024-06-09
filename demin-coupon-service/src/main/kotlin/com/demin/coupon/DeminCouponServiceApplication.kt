package com.demin.coupon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.demin"])
class DeminCouponServiceApplication

fun main(args: Array<String>) {
    runApplication<DeminCouponServiceApplication>(*args)
}
