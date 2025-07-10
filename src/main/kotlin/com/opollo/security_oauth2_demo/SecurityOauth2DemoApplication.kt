package com.opollo.security_oauth2_demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class SecurityOauth2DemoApplication

fun main(args: Array<String>) {
	runApplication<SecurityOauth2DemoApplication>(*args)
}
