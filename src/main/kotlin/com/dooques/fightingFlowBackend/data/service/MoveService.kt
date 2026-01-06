package com.dooques.fightingFlowBackend.data.service

import com.dooques.fightingFlowBackend.data.dto.MoveDto
import com.dooques.fightingFlowBackend.data.dto.toEntity
import com.dooques.fightingFlowBackend.data.entities.toDto
import com.dooques.fightingFlowBackend.data.repository.MoveRepository
import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions
import com.dooques.fightingFlowBackend.exceptions.move.MoveExceptions
import org.springframework.stereotype.Service

@Service
class MoveService(
    private val moveRepository: MoveRepository
) {

    /*
    ---------------------------
        Search Functions
    ---------------------------
    */

    fun getMovesByName(name: String): List<MoveDto> =
        moveRepository.findAllByName(name)
            .map { it.toDto() }
            .ifEmpty {
                throw MoveExceptions.NoMoveFoundException(name)
            }

    fun getAllMoves(): List<MoveDto> =
        moveRepository.findAll()
            .map { it.toDto() }
            .ifEmpty {
                val error = MoveExceptions.NoMovesFoundException()
                println(error.message)
                throw error
            }

    fun getAllMovesByFighter(fighter: String): List<MoveDto> =
         moveRepository.getAllMovesByFighter(fighter)
            .map { it.toDto() }
            .ifEmpty {
                val error = MoveExceptions.NoMovesFoundException()
                println(error.message)
                throw error
            }

    fun getAllMovesByGame(game: String): List<MoveDto> {
        return moveRepository.getAllMovesByGame(game)
            .map { it.toDto() }
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

    fun postMove(
        moveDto: MoveDto,
        postMultiple: Boolean = false
    ): MoveDto {
        // Search for existing moves, save or update them based on input
        var originalMove: MoveDto? = null
        runCatching {
            println("Saving Move: $moveDto")
            originalMove = getMovesByName(
                moveDto.name ?: throw FightingFlowExceptions.InvalidIdException()
            ).firstOrNull()
        }
            .onFailure { e ->
                if (e is FightingFlowExceptions.InvalidIdException) {
                    throw e
                } else {
                    println("No existing moves found, saving new move.")
                    return moveRepository
                        .save(moveDto.toEntity())
                        .toDto()
                }
            }
            .onSuccess {
                println("Existing move found.")
                val error = MoveExceptions.MoveAlreadyExistsException()
                println(error.message)
                if (postMultiple && originalMove != null) {
                    if (moveDto != originalMove)
                        return moveRepository
                            .save(moveDto.toEntity())
                            .toDto()
                } else {
                    throw error
                }
            }
        val error = MoveExceptions.PostFunctionFailedException("Failed without reason")
        println(error.message)
        throw error
    }

    fun updateMove(moveDto: MoveDto): MoveDto {
        var updatedMove = MoveDto()

        runCatching {
            val originalMove = getMovesByName(
                moveDto.name ?: throw FightingFlowExceptions.InvalidIdException()
            ).firstOrNull() ?: throw MoveExceptions.NoMoveFoundException(moveDto.name ?: "")

            if (originalMove == moveDto) {
                throw MoveExceptions.InvalidMoveException(
                    moveDto.id ?: 0,
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
                name = moveDto.name.takeIf { it != originalMove.name } ?: originalMove.name,
                notation = moveDto.notation?.takeIf { it != originalMove.notation } ?: originalMove.notation,
                type = moveDto.type?.takeIf { it != originalMove.type } ?: originalMove.type,
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

    fun deleteMove(name: String) {
        runCatching {
            val move = getMovesByName(name)
                .firstOrNull() ?: throw MoveExceptions.NoMoveFoundException(name)
            moveRepository.delete(move.toEntity())
        }
            .onFailure {
                val error = MoveExceptions.DeleteFunctionFailedException("Failed without reason.")
                println(error.message)
                throw error
            }
    }

    fun deleteAllMoves() {
        moveRepository.deleteAll()
    }
}