package com.dooques.fightingflow.data.service

import com.dooques.fightingflow.data.dto.CharacterDto
import com.dooques.fightingflow.data.dto.toEntity
import com.dooques.fightingflow.data.entities.CharacterEntity
import com.dooques.fightingflow.data.entities.toDto
import com.dooques.fightingflow.data.repository.CharacterRepository
import com.dooques.fightingflow.exceptions.FightingFlowExceptions
import org.springframework.stereotype.Service

@Service
class CharacterService(
    private val characterRepository: CharacterRepository
) {
    // Search Functions

    fun getAllCharacters(): List<CharacterDto> =
        characterRepository.findAll().map { it.toDto() }

    fun getCharacterById(id: Long): CharacterDto =
        characterRepository.findById(id)
            .map { it.toDto() }
            .orElseThrow {
                FightingFlowExceptions.Character.CharacterNotFoundException(id.toString())
            }

    fun getCharacterByName(name: String): CharacterDto =
        characterRepository.findByName(name)?.toDto()
            ?: throw FightingFlowExceptions.Character.CharacterNotFoundException(name)

    fun getCharactersByGame(game: String): List<CharacterDto> =
        characterRepository.findAllByGame(game)
            .map(CharacterEntity::toDto)

    fun getCharacterByNameAndGame(name: String, game: String): List<CharacterDto> =
        characterRepository.findAllByGameAndName(game, name)
            .map(CharacterEntity::toDto)

    fun getCustomCharacters(): List<CharacterDto> =
        characterRepository.findAllByMutableTrue()
            .map(CharacterEntity::toDto)

    // Action Functions

    fun saveCharacter(characterDto: CharacterDto): CharacterDto =
        characterRepository.save(characterDto.toEntity().apply {id = 0}).toDto()

    fun updateCharacter(characterDto: CharacterDto): CharacterDto =
        characterRepository.save(characterDto.toEntity()).toDto()

    fun deleteCharacter(characterId: Long) {
        val characterToDelete = getCharacterById(characterId).toEntity()
        characterRepository.delete(characterToDelete)
    }
}