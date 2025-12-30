package com.dooques.fightingFlowBackend.data.repository

import com.dooques.fightingFlowBackend.data.entities.CharacterEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CharacterRepository: JpaRepository<CharacterEntity, Long> {
    fun findByName(name: String): CharacterEntity?
    fun findAllByGame(game: String): List<CharacterEntity>
    fun findAllByGameAndName(game: String, name: String): List<CharacterEntity>
    fun findAllByMutableTrue(): List<CharacterEntity>
}