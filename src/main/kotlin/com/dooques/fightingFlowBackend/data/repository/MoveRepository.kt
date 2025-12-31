package com.dooques.fightingFlowBackend.data.repository

import com.dooques.fightingFlowBackend.data.entities.MoveEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MoveRepository : JpaRepository<MoveEntity, Long> {
    fun findByName(name: String): MoveEntity?
    fun getAllMovesByFighter(character: String): List<MoveEntity>
    fun getAllMovesByGame(game: String): List<MoveEntity>
}