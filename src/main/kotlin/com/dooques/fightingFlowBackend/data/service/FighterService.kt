package com.dooques.fightingFlowBackend.data.service

import com.dooques.fightingFlowBackend.data.dto.FighterDto
import com.dooques.fightingFlowBackend.data.dto.toEntity
import com.dooques.fightingFlowBackend.data.entities.FighterEntity
import com.dooques.fightingFlowBackend.data.entities.toDto
import com.dooques.fightingFlowBackend.data.repository.FighterRepository
import com.dooques.fightingFlowBackend.exceptions.fighter.FighterExceptions
import com.dooques.fightingFlowBackend.exceptions.move.MoveExceptions
import org.springframework.stereotype.Service

@Service
class FighterService(
    private val fighterRepository: FighterRepository
) {
    // Search Functions

    fun getAllFighters(): List<FighterDto> =
        fighterRepository.findAll().map { it.toDto() }

    fun getFighterById(id: Long): FighterDto =
        fighterRepository.findById(id)
            .map { it.toDto() }
            .orElseThrow {
                val error = FighterExceptions.NoFighterFoundException(id)
                println(error.message)
                error
            }

    fun getFighterByName(name: String): FighterDto {
        val fighter = fighterRepository.findByName(name)?.toDto()
        return if (fighter != null) fighter
        else {
            val error = FighterExceptions.NoFighterFoundByNameException(name)
            println(error.message)
            throw error
        }
    }

    fun getFightersByGame(game: String): List<FighterDto> =
        fighterRepository.findAllByGame(game)
            .map(FighterEntity::toDto)
            .ifEmpty {
                val error = FighterExceptions.NoFightersFoundException()
                println(error.message)
                throw error
            }

    fun getFighterByNameAndGame(name: String, game: String): List<FighterDto> =
        fighterRepository.findAllByGameAndName(game, name)
            .map(FighterEntity::toDto)
            .ifEmpty {
                val error = FighterExceptions.NoFightersFoundException()
                println(error.message)
                throw error
            }

    fun getCustomFighters(): List<FighterDto> =
        fighterRepository.findAllByMutableTrue()
            .map(FighterEntity::toDto)
            .ifEmpty {
                val error = FighterExceptions.NoFightersFoundException()
                println(error.message)
                throw error
            }

    // Action Functions

    fun postFighter(
        fighterDto: FighterDto,
        postMultiple: Boolean = false
    ): FighterDto {
        // Search for existing characters, save or update them based on input.
        var originalFighter: FighterDto? = null
        runCatching {
            println("Saving Fighter: $fighterDto")
            originalFighter = getFighterByName(fighterDto.name)
        }
            .onFailure {
                println("No existing fighters found, saving new fighter.")
                return fighterRepository
                    .save(fighterDto.toEntity().apply { id = 0 })
                    .toDto()
            }
            .onSuccess {
                println("Existing fighter found.")
                if (postMultiple && originalFighter != null) {
                    if (fighterDto != originalFighter)
                        return updateFighter(fighterDto)
                } else {
                   throw MoveExceptions.MoveAlreadyExistsException()
                }
            }
       throw FighterExceptions.PostFunctionFailedException("Failed without reason")
    }

    fun updateFighter(fighterDto: FighterDto): FighterDto {
        var updatedCharacter = FighterDto()
        runCatching {
            val originalFighter = getFighterByName(fighterDto.name)

            if (originalFighter == fighterDto) {
                val e = FighterExceptions.InvalidFighterException(
                    fighterDto.id ?: 0,
                    mapOf ("Invalid Change" to "No changes detected")
                )
                println(e.message)
                throw e
            }
            println("""
                Original Fighter: $originalFighter
                Updated Fighter: $fighterDto
            **************************************
            """)

            updatedCharacter = originalFighter.copy(
                imageId = fighterDto.imageId?.takeIf { it != originalFighter.imageId } ?: originalFighter.imageId,
                imageUrl = fighterDto.imageUrl?.takeIf { it != originalFighter.imageUrl } ?: originalFighter.imageUrl,
                fightingStyle = fighterDto.fightingStyle?.takeIf { it != originalFighter.fightingStyle } ?: originalFighter.fightingStyle,
                combosById = fighterDto.combosById?.takeIf { it != originalFighter.combosById } ?: originalFighter.combosById,
                game = fighterDto.game?.takeIf { it != originalFighter.game } ?: originalFighter.game,
                controlType = fighterDto.controlType?.takeIf { it != originalFighter.controlType } ?: originalFighter.controlType,
                numpadNotation = fighterDto.numpadNotation?.takeIf { it != originalFighter.numpadNotation } ?: originalFighter.numpadNotation,
                uniqueMoves = fighterDto.uniqueMoves?.takeIf { it != originalFighter.uniqueMoves },
            )

            if (originalFighter == updatedCharacter) throw FighterExceptions.InvalidFighterException(
                fighterDto.id ?: 0,
                mapOf("Invalid Change" to "No changes detected")
            )
        }
            .onSuccess {
                return fighterRepository
                    .save(updatedCharacter.toEntity())
                    .toDto()
            }
            .onFailure { e ->
                var error = e
                if (e !is FighterExceptions.NoFighterFoundByNameException) {
                    println(error.message)
                    throw e
                }
                else {
                    error = FighterExceptions.NoFighterFoundByNameException(fighterDto.name)
                    println(e.message)
                    throw error
                }
            }
        val error = FighterExceptions.PutFunctionFailedException("Failed without reason")
        println(error.message)
        throw error
    }

    fun deleteFighter(fighterId: Long) {
        runCatching {
            val fighterToDelete = getFighterById(fighterId).toEntity()
            fighterRepository.delete(fighterToDelete)
        }
            .onFailure { e ->
                val error = FighterExceptions.DeleteFunctionFailedException(e.message ?: "Failed without reason.")
                println(error.message)
                throw error
            }
    }

    fun deleteAllFighters() {
        fighterRepository.deleteAll()
    }
}