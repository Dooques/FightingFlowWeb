package com.dooques.fightingFlowBackend.data.service

import com.dooques.fightingFlowBackend.data.dto.CharacterDto
import com.dooques.fightingFlowBackend.data.dto.toEntity
import com.dooques.fightingFlowBackend.data.entities.CharacterEntity
import com.dooques.fightingFlowBackend.data.entities.toDto
import com.dooques.fightingFlowBackend.data.repository.CharacterRepository
import com.dooques.fightingFlowBackend.exceptions.character.CharacterExceptions
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
                CharacterExceptions.NoCharacterFoundException(id)
            }

    fun getCharacterByName(name: String): CharacterDto =
        characterRepository.findByName(name)?.toDto()
            ?: throw CharacterExceptions.NoCharacterFoundByNameException(name)

    fun getCharactersByGame(game: String): List<CharacterDto> =
        characterRepository.findAllByGame(game)
            .map(CharacterEntity::toDto)
            .ifEmpty { throw CharacterExceptions.NoCharactersFoundException() }

    fun getCharacterByNameAndGame(name: String, game: String): List<CharacterDto> =
        characterRepository.findAllByGameAndName(game, name)
            .map(CharacterEntity::toDto)
            .ifEmpty { throw CharacterExceptions.NoCharactersFoundException() }

    fun getCustomCharacters(): List<CharacterDto> =
        characterRepository.findAllByMutableTrue()
            .map(CharacterEntity::toDto)
            .ifEmpty { throw CharacterExceptions.NoCharactersFoundException() }

    // Action Functions

    fun saveCharacter(characterDto: CharacterDto): CharacterDto {
        // Search for existing characters, if none found, save new character.
        runCatching { getCharacterByName(characterDto.name) }
            .onFailure {
                return characterRepository
                    .save(characterDto.toEntity().apply { id = 0 })
                    .toDto()
            }
            .onSuccess {
                throw CharacterExceptions.CharacterAlreadyExistsException()
            }
       throw CharacterExceptions.PostFunctionFailedException("Failed without reason")
    }

    fun updateCharacter(characterDto: CharacterDto): CharacterDto {
        var updatedCharacter = CharacterDto()
        runCatching {
            val originalCharacter = getCharacterByName(characterDto.name)

            if (originalCharacter == characterDto) {
                throw CharacterExceptions.InvalidCharacterException(
                    characterDto.id ?: 0,
                    mapOf ("Invalid Change" to "No changes detected")
                )
            }

            updatedCharacter = originalCharacter.copy(
                imageId = characterDto.imageId?.takeIf { it != originalCharacter.imageId },
                imageUri = characterDto.imageUri?.takeIf { it != originalCharacter.imageUri },
                fightingStyle = characterDto.fightingStyle?.takeIf { it != originalCharacter.fightingStyle },
                combosById = characterDto.combosById?.takeIf { it != originalCharacter.combosById },
                game = characterDto.game?.takeIf { it != originalCharacter.game },
                controlType = characterDto.controlType?.takeIf { it != originalCharacter.controlType },
                numpadNotation = characterDto.numpadNotation?.takeIf { it != originalCharacter.numpadNotation },
                uniqueMoves = characterDto.uniqueMoves?.takeIf { it != originalCharacter.uniqueMoves },
            )
        }
            .onSuccess {
                characterRepository
                    .save(updatedCharacter.toEntity())
                    .toDto()
            }
            .onFailure {
                throw CharacterExceptions.NoCharacterFoundByNameException(characterDto.name)
            }
        throw CharacterExceptions.PutFunctionFailedException("Failed without reason")
    }

    fun deleteCharacter(characterId: Long) {
        val characterToDelete = getCharacterById(characterId).toEntity()
        characterRepository.delete(characterToDelete)
    }
}