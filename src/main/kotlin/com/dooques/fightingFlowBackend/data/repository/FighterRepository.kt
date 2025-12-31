package com.dooques.fightingFlowBackend.data.repository

import com.dooques.fightingFlowBackend.data.entities.FighterEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FighterRepository: JpaRepository<FighterEntity, Long> {
    fun findByName(name: String): FighterEntity?
    fun findAllByGame(game: String): List<FighterEntity>
    fun findAllByGameAndName(game: String, name: String): List<FighterEntity>
    fun findAllByMutableTrue(): List<FighterEntity>
}