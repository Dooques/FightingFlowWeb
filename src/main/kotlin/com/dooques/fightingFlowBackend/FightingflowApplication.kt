package com.dooques.fightingFlowBackend

import com.dooques.fightingFlowBackend.data.config.DatabaseConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(DatabaseConfig::class)
class FightingflowApplication

fun main(args: Array<String>) {
	runApplication<FightingflowApplication>(*args)
}
