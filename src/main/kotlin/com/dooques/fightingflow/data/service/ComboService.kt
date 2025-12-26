package com.dooques.fightingflow.data.service

import com.dooques.fightingflow.data.config.DatabaseConfig
import com.dooques.fightingflow.data.dto.ComboDto
import com.dooques.fightingflow.data.dto.toEntity
import com.dooques.fightingflow.data.entities.toDto
import com.dooques.fightingflow.data.repository.ComboRepository
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.Combo.InvalidComboException
import com.google.api.client.util.Data
import org.springframework.stereotype.Service
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.Combo as Combo

@Service
class ComboService(
    private val comboRepository: ComboRepository,
    private val quotesConfig: DatabaseConfig.ValidationConfig
) {

    /*
    ---------------------------
        Search Functions
    ---------------------------
    */

    fun getComboById(id: Long): ComboDto =
        comboRepository.findById(id)
            .map { it.toDto() }
            .orElseThrow { Combo.NoComboFoundException(id) }

    fun getAllCombos(): List<ComboDto> =
        comboRepository.findAll()
            .map { it.toDto() }
            .ifEmpty { throw Combo.NoCombosFoundException() }

    fun getCombosByUser(creator: String): List<ComboDto> =
        comboRepository.getAllCombosByCreator(creator)
            .map { it.toDto()}
            .ifEmpty { throw Combo.NoCombosFoundException() }

    fun getCombosByCharacter(character: String): List<ComboDto> =
        comboRepository.findByCharacter(character)
            .map { it.toDto() }
            .ifEmpty { throw Combo.NoCombosFoundException() }

    fun getCombosByTitle(title: String): List<ComboDto> =
        comboRepository.findAllByTitle(title)
            .map { it.toDto() }
            .ifEmpty { throw Combo.NoCombosFoundByTitleException(title) }

    /*
    ---------------------------
        Action Functions
    ---------------------------
    */

    fun saveCombo(combo: ComboDto): ComboDto {
        runCatching {
            comboRepository.findById(combo.id)
        }
            .onFailure {
                comboRepository
                    .save(combo.toEntity().apply { id = 0 })
                    .toDto()
            }
            .onSuccess {
                throw Combo.ComboAlreadyExistsException()
            }
        throw Combo.PostFunctionFailedException("Failed without reason")
    }

    fun updateCombo(comboDto: ComboDto): ComboDto {
        var updatedCombo = ComboDto()

        runCatching {

            val comboId = comboDto.id ?: throw InvalidComboException(
                comboDto.id ?: 0,
                Data.mapOf("id" to "Id is null or invalid"),
            )

            val originalCombo = getComboById(comboId)

            if (originalCombo == comboDto) throw InvalidComboException(
                comboDto.id,
                Data.mapOf("Invalid Change" to "No changes detected"),
            )

            println("""
            **************************************
                Updating Combo with id ${comboDto.id}
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
                throw Combo.PutFunctionFailedException(result.message ?: "Failed without reason.")
            }
        throw Combo.PutFunctionFailedException("Failed without reason")
    }

    fun deleteCombo(comboId: Long) {
        runCatching {
            val comboToDelete = getComboById(comboId).toEntity()
            comboRepository.delete(comboToDelete)
        }
            .onFailure { result ->
                throw Combo.DeleteFunctionFailedException(result.message ?: "Failed without reason.")
            }
    }
}