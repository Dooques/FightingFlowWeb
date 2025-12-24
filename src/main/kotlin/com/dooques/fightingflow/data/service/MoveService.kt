package com.dooques.fightingflow.data.service

import com.dooques.fightingflow.data.dto.MoveDto
import com.dooques.fightingflow.data.entities.MoveEntity
import com.dooques.fightingflow.data.entities.toDto
import com.dooques.fightingflow.data.repository.MoveRepository
import org.springframework.stereotype.Service

@Service
class MoveService(private val moveRepository: MoveRepository) {

    fun getMove(name: String): MoveDto {
        return moveRepository.findByName(name)
            ?.toDto() ?: throw Exception("No Moves Found")
    }

    fun getMoveById(id: Long): List<MoveDto> {
        return listOf(moveRepository.findById(id)
            .map(MoveEntity::toDto)
            .orElseThrow { Exception("No Moves Found") })
    }

    fun getAllMoves(): List<MoveDto> {
        return moveRepository.findAll()
            .map(MoveEntity::toDto)
    }

    fun getAllMovesByCharacter(character: String): List<MoveDto> {
        return moveRepository.getAllMovesByCharacter(character)
            .map(MoveEntity::toDto)
    }

    fun getAllMovesByGame(game: String): List<MoveDto> {
        return moveRepository.getAllMovesByGame(game)
            .map(MoveEntity::toDto)
    }
}