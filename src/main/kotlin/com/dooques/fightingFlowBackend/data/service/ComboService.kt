package com.dooques.fightingFlowBackend.data.service

import com.dooques.fightingFlowBackend.data.config.DatabaseConfig
import com.dooques.fightingFlowBackend.data.dto.ComboDto
import com.dooques.fightingFlowBackend.data.dto.toEntity
import com.dooques.fightingFlowBackend.data.entities.toDto
import com.dooques.fightingFlowBackend.data.repository.ComboRepository
import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions
import com.dooques.fightingFlowBackend.exceptions.combo.ComboExceptions
import com.google.api.client.util.Data
import org.springframework.stereotype.Service

@Service
class ComboService(
    private val comboRepository: ComboRepository,
    private val databaseConfig: DatabaseConfig.ValidationConfig
) {
    /*
    ---------------------------
        Search Functions
    ---------------------------
    */

    fun getComboById(id: Long): ComboDto =
        comboRepository.findById(id)
            .map { it.toDto() }
            .orElseThrow {
                val error = ComboExceptions.NoComboFoundException(id)
                println(error.message)
                error
            }

    fun getCombosByUser(creator: String): List<ComboDto> =
        comboRepository.getAllCombosByCreator(creator)
            .map { it.toDto()}
            .ifEmpty {
                val error = ComboExceptions.NoCombosFoundException()
                println(error.message)
                throw error
            }

    fun getCombosByFighter(fighter: String): List<ComboDto> =
        comboRepository.findByFighter(fighter)
            .map { it.toDto() }
            .ifEmpty {
                val error = ComboExceptions.NoCombosFoundException()
                println(error.message)
                throw error
            }

    fun getCombosByTitle(title: String): List<ComboDto> =
        comboRepository.findAllByTitle(title)
            .map { it.toDto() }
            .ifEmpty {
                val error = ComboExceptions.NoCombosFoundByTitleException(title)
                println(error.message)
                throw error
            }

    fun getAllCombos(): List<ComboDto> =
        comboRepository.findAll()
            .map { it.toDto() }
            .ifEmpty {
                val error = ComboExceptions.NoCombosFoundException()
                println(error.message)
                throw error
            }

    /*
    ---------------------------
        Action Functions
    ---------------------------
    */

    fun saveCombo(comboDto: ComboDto): ComboDto {
        runCatching {
            comboRepository.findById(comboDto.id ?: throw FightingFlowExceptions.InvalidIdException())
        }
            .onFailure {

                println("""
                **************************************
                    Posting Combo: $comboDto
                **************************************
                """)

                return comboRepository
                    .save(comboDto.toEntity().apply { id = 0 })
                    .toDto()
            }
            .onSuccess {
                val error = ComboExceptions.ComboAlreadyExistsException()
                println(error.message)
                throw error
            }
        val error = ComboExceptions.PostFunctionFailedException("Failed without reason")
        println(error.message)
        throw error
    }

    fun updateCombo(comboDto: ComboDto): ComboDto {
        var updatedCombo = ComboDto()

        runCatching {
            val comboId = comboDto.id ?: throw ComboExceptions.InvalidComboException(
                comboDto.id ?: 0,
                Data.mapOf("id" to "Id is null or invalid"),
            )

            val originalCombo = getComboById(comboId)

            if (originalCombo == comboDto) throw ComboExceptions.InvalidComboException(
                comboDto.id,
                Data.mapOf("Invalid Change" to "No changes detected"),
            )

            println("""
            **************************************
                Original Combo: $originalCombo
                Updated Combo: $comboDto
            **************************************
            """)

            updatedCombo = comboDto.copy(
                title = comboDto.title?.takeIf { it != originalCombo.title } ?: originalCombo.title,
                damage = comboDto.damage?.takeIf { it != originalCombo.damage } ?: originalCombo.damage,
                difficulty = comboDto.difficulty?.takeIf { it != originalCombo.difficulty } ?: originalCombo.difficulty,
                tags = comboDto.tags?.takeIf { it != originalCombo.tags } ?: originalCombo.tags,
                game = comboDto.game?.takeIf { it != originalCombo.game } ?: originalCombo.game,
                controlType = comboDto.controlType?.takeIf { it != originalCombo.controlType } ?: originalCombo.controlType,
                moves = comboDto.moves?.takeIf { it != originalCombo.moves } ?: originalCombo.moves,
            )
        }
            .onSuccess {
                return comboRepository
                    .save(updatedCombo.toEntity())
                    .toDto()
            }
            .onFailure { result ->
                throw ComboExceptions.PutFunctionFailedException(result.message ?: "Failed without reason.")
            }
        throw ComboExceptions.PutFunctionFailedException("Failed without reason")
    }

    fun deleteCombo(comboId: Long) {
        runCatching {
            val comboToDelete = getComboById(comboId).toEntity()
            comboRepository.delete(comboToDelete)
        }
            .onFailure { result ->
                val error = ComboExceptions.DeleteFunctionFailedException(result.message ?: "Failed without reason.")
                println(error.message)
                throw error
            }
    }
}