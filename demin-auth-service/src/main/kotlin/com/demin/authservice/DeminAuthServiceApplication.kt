package com.demin.authservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DeminAuthServiceApplication

fun main(args: Array<String>) {
	runApplication<DeminAuthServiceApplication>(*args)
}