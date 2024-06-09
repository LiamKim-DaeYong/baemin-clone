package com.demin.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.demin"])
class DeminAuthServiceApplication

fun main(args: Array<String>) {
	runApplication<DeminAuthServiceApplication>(*args)
}
