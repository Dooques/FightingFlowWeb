package com.dooques.fightingFlowBackend.data.service

import com.dooques.fightingFlowBackend.data.dto.MoveDto
import com.dooques.fightingFlowBackend.data.dto.toEntity
import com.dooques.fightingFlowBackend.data.entities.MoveEntity
import com.dooques.fightingFlowBackend.data.entities.toDto
import com.dooques.fightingFlowBackend.data.repository.MoveRepository
import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions
import com.dooques.fightingFlowBackend.exceptions.move.MoveExceptions
import org.springframework.stereotype.Service

@Service
class MoveService(private val moveRepository: MoveRepository) {

    /*
    ---------------------------
        Search Functions
    ---------------------------
    */

    fun getMoveById(id: Long): MoveDto =
        moveRepository.findById(id)
            .map(MoveEntity::toDto)
            .orElseThrow {
                val error = MoveExceptions.NoMoveFoundException(id)
                println(error.message)
                error
            }

    fun getAllMoves(): List<MoveDto> =
        moveRepository.findAll()
            .map(MoveEntity::toDto)
            .ifEmpty {
                val error = MoveExceptions.NoMovesFoundException()
                println(error.message)
                throw error
            }

    fun getAllMovesByFighter(fighter: String): List<MoveDto> =
         moveRepository.getAllMovesByFighter(fighter)
            .map(MoveEntity::toDto)
            .ifEmpty {
                val error = MoveExceptions.NoMovesFoundException()
                println(error.message)
                throw error
            }

    fun getAllMovesByGame(game: String): List<MoveDto> {
        return moveRepository.getAllMovesByGame(game)
            .map(MoveEntity::toDto)
            .ifEmpty {
                val error = MoveExceptions.NoMovesFoundException()
                println(error.message)
                throw error
            }
    }

    /*
   ---------------------------
       Action Functions
   ---------------------------
   */

    fun postMove(moveDto: MoveDto): MoveDto {
        runCatching {
            getMoveById(moveDto.id ?: throw FightingFlowExceptions.InvalidIdException())
        }
            .onFailure {

                println("""
                **************************************
                    Posting Move: $moveDto
                **************************************
                """)

                return moveRepository.save(moveDto.toEntity()).toDto()
            }
            .onSuccess {
                val error = MoveExceptions.MoveAlreadyExistsException()
                println(error.message)
                throw error
            }
        val error = MoveExceptions.PostFunctionFailedException("Failed without reason")
        println(error.message)
        throw error
    }

    fun updateMove(moveDto: MoveDto): MoveDto {
        var updatedMove = MoveDto()

        runCatching {
            val originalMove = getMoveById(
                moveDto.id ?: throw FightingFlowExceptions.InvalidIdException()
            )

            if (originalMove == moveDto) {
                throw MoveExceptions.InvalidMoveException(
                    moveDto.id,
                    mapOf("Invalid Change" to "No changes detected")
                )
            }

            println("""
            **************************************
                Original Move: $originalMove
                Updated Move: $moveDto
            **************************************
            """)

            updatedMove = moveDto.copy(
                name = moveDto.name?.takeIf { it != originalMove.name } ?: originalMove.name,
                notation = moveDto.notation?.takeIf { it != originalMove.notation } ?: originalMove.notation,
                moveType = moveDto.moveType?.takeIf { it != originalMove.moveType } ?: originalMove.moveType,
            )
        }
            .onSuccess {
                return moveRepository
                    .save(updatedMove.toEntity())
                    .toDto()
            }
            .onFailure { result ->
                val error = MoveExceptions.PutFunctionFailedException(result.message ?: "Failed without reason.")
                println(error.message)
                throw error
            }
        val error = MoveExceptions.PutFunctionFailedException("Failed without reason")
        println(error.message)
        throw error
    }

    fun deleteMove(id: Long) {
        runCatching {
            val move = getMoveById(id)
            moveRepository.delete(move.toEntity())
        }
            .onFailure {
                val error = MoveExceptions.DeleteFunctionFailedException("Failed without reason.")
                println(error.message)
                throw error
            }
    }
}