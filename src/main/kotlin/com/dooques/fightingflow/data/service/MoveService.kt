package com.dooques.fightingflow.data.service

import com.dooques.fightingflow.data.dto.MoveDto
import com.dooques.fightingflow.data.dto.toEntity
import com.dooques.fightingflow.data.entities.MoveEntity
import com.dooques.fightingflow.data.entities.toDto
import com.dooques.fightingflow.data.repository.MoveRepository
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.Move
import org.springframework.stereotype.Service

@Service
class MoveService(private val moveRepository: MoveRepository) {

    /*
    ---------------------------
        Search Functions
    ---------------------------
    */

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

    /*
   ---------------------------
       Action Functions
   ---------------------------
   */

    fun postMove(move: MoveDto): MoveDto {
        return if (move.id != null) {
            val existingMoves = getMoveById(move.id)
            if (existingMoves.isNotEmpty())
                moveRepository.save(move.toEntity()).toDto()
            else
                throw Move.MoveAlreadyExistsException()
        } else throw Move.NoMoveFoundException(0)
    }

    fun updateMove(move: MoveDto): MoveDto {
        if (move.id == null) {
            moveRepository.findById(move.id)
        } else throw Move.NoMoveFoundException(0)

        return moveRepository.save(move.toEntity()).toDto()
    }

    fun deleteMove(id: Long) {
        val move = getMoveById(id).first()
        moveRepository.delete(move.toEntity())
    }
}