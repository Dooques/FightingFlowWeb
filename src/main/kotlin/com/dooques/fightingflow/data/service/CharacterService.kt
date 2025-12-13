package com.dooques.fightingflow.data.service

import com.dooques.fightingflow.data.dto.CharacterDto
import com.dooques.fightingflow.data.dto.toEntity
import com.dooques.fightingflow.data.entities.CharacterEntity
import com.dooques.fightingflow.data.entities.toDto
import com.dooques.fightingflow.data.repository.CharacterRepository
import org.springframework.stereotype.Service

@Service
class CharacterService(
    private val characterRepository: CharacterRepository
) {
    // Search Functions

    fun getAllCharacters(): List<CharacterDto> =
        characterRepository.findAll().map { it.toDto() }

    fun getCharacter(name: String): CharacterDto =
        characterRepository.findByName(name)?.toDto() ?: throw Exception("Character not found")

    fun getCharactersByGame(game: String): List<CharacterDto> =
        characterRepository.findAllByGame(game).map(CharacterEntity::toDto)

    fun getCharacterByNameAndGame(name: String, game: String): List<CharacterDto> =
        characterRepository.findAllByGameAndName(game, name).map(CharacterEntity::toDto)

    fun getCustomCharacters(): List<CharacterDto> =
        characterRepository.findAllByMutableTrue().map(CharacterEntity::toDto)

    // Action Functions

    fun saveCharacter(characterEntity: CharacterEntity): CharacterDto =
        characterRepository.save(characterEntity).toDto()

    suspend fun updateCharacter(characterDto: CharacterDto): CharacterDto =
        characterRepository.save(characterDto.toEntity()).toDto()

    fun deleteCharacter(characterEntity: CharacterEntity) =
        characterRepository.delete(characterEntity)
}