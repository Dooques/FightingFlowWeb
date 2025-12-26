package com.dooques.fightingflow.data.service

import com.dooques.fightingflow.data.dto.MoveDto
import com.dooques.fightingflow.data.dto.toEntity
import com.dooques.fightingflow.data.entities.MoveEntity
import com.dooques.fightingflow.data.entities.toDto
import com.dooques.fightingflow.data.repository.MoveRepository
import com.dooques.fightingflow.exceptions.FightingFlowExceptions
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.Move
import org.springframework.stereotype.Service

@Service
class MoveService(private val moveRepository: MoveRepository) {

    /*
    ---------------------------
        Search Functions
    ---------------------------
    */

    fun getMoveById(id: Long): MoveDto {
        val move = (moveRepository.findById(id)
            .map(MoveEntity::toDto))
            .orElseThrow { Move.NoMoveFoundException(id) }
        return move
    }

    fun getAllMoves(): List<MoveDto> {
        return moveRepository.findAll()
            .map(MoveEntity::toDto)
            .ifEmpty { throw Move.NoMovesFoundException() }
    }

    fun getAllMovesByCharacter(character: String): List<MoveDto> {
        return moveRepository.getAllMovesByCharacter(character)
            .map(MoveEntity::toDto)
            .ifEmpty { throw Move.NoMovesFoundException() }
    }

    fun getAllMovesByGame(game: String): List<MoveDto> {
        return moveRepository.getAllMovesByGame(game)
            .map(MoveEntity::toDto)
            .map { throw Move.NoMovesFoundException() }
    }

    /*
   ---------------------------
       Action Functions
   ---------------------------
   */

    fun postMove(move: MoveDto): MoveDto {
        runCatching {
            getMoveById(move.id ?: throw FightingFlowExceptions.InvalidIdException())
        }
            .onFailure {
                return moveRepository.save(move.toEntity()).toDto()
            }
            .onSuccess {
                throw Move.MoveAlreadyExistsException()
            }
        throw Move.PostFunctionFailedException("Failed without reason")
    }

    fun updateMove(moveDto: MoveDto): MoveDto {
        var updatedMove = MoveDto()

        runCatching {
            val originalMove = getMoveById(
                moveDto.id ?: throw FightingFlowExceptions.InvalidIdException()
            )

            if (originalMove == moveDto) {
                throw Move.InvalidMoveException(
                    moveDto.id,
                    mapOf("Invalid Change" to "No changes detected")
                )
            }

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
                throw Move.PutFunctionFailedException(result.message ?: "Failed without reason.")
            }
        throw Move.PutFunctionFailedException("Failed without reason")
    }

    fun deleteMove(id: Long) {
        runCatching {
            val move = getMoveById(id)
            moveRepository.delete(move.toEntity())
        }
            .onFailure { throw Move.DeleteFunctionFailedException("Failed without reason.") }
    }
}