package com.dooques.fightingFlowBackend.data.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate

@Configuration
class RestConfig {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate().apply {
        errorHandler = ResponseErrorHandler { response ->
            response.body.use {
                it.readBytes().decodeToString().startsWith("ERROR")
            }
        }
    }
}