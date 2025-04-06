package com.practice.spring.kotlin.sprint_boot_in_kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SprintBootInKotlinApplication

fun main(args: Array<String>) {
	runApplication<SprintBootInKotlinApplication>(*args)
}
