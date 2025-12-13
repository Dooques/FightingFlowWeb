package com.dooques.fightingflow.data.service

import com.dooques.fightingflow.data.config.DatabaseConfig
import com.dooques.fightingflow.data.dto.ComboDto
import com.dooques.fightingflow.data.dto.toEntity
import com.dooques.fightingflow.data.entities.ComboEntity
import com.dooques.fightingflow.data.entities.toDto
import com.dooques.fightingflow.data.repository.ComboRepository
import org.springframework.stereotype.Service
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.Combo as Exceptions

@Service
class ComboService(
    private val comboRepository: ComboRepository,
    private val quotesConfig: DatabaseConfig.ValidationConfig
) {

    init {
        println(quotesConfig)
    }
    // Search Functions

    fun getComboById(id: Long): ComboDto? =
        comboRepository.findById(id)
            .map { it.toDto() }
            .orElseThrow { Exceptions.ComboNotFoundException(id) }

    fun getAllCombos(): List<ComboDto> =
        comboRepository.findAll().map { it.toDto() }
            .ifEmpty { throw Exceptions.NoCombosFoundException() }

    fun getCombosByUser(creator: String): List<ComboDto> =
        comboRepository.getAllCombosByCreator(creator).map { it.toDto()}

    fun getCombosByCharacter(character: String): List<ComboDto> =
        comboRepository.findByCharacter(character).map { it.toDto() }

    // Action Functions

    fun saveCombo(combo: ComboDto): ComboDto =
        comboRepository
            .save(combo.toEntity().apply { id = 0 })
            .toDto()

    fun updateCombo(combo: ComboDto): ComboDto =
        comboRepository
            .save(combo.toEntity())
            .toDto()

    fun deleteCombo(combo: ComboEntity) =
        comboRepository.delete(combo)
}