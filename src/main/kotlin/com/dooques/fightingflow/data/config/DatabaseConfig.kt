package com.dooques.fightingflow.data.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "combos")
data class DatabaseConfig(
    val search: SearchConfig = SearchConfig(),
    val comboValidation: ValidationConfig = ValidationConfig()
    ) {

    @Configuration
    data class SearchConfig(
        val ignoreCase: Boolean = true,
    )

    @Configuration
    data class ValidationConfig(
        val minMoves: Int = 2,
        val titleLength: Int = 10,
        val damageRange: IntRange = 1..9999,
        val creatorLength: Int = 10,
        val difficultyRange: IntRange = 0..5,
        val gameLength: Int = 5,
        val movesLength: Int = 2
    )
}