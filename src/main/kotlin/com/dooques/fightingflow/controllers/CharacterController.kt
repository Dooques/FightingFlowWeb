package com.dooques.fightingflow.controllers

import com.dooques.fightingflow.data.dto.CharacterDto
import com.dooques.fightingflow.data.service.CharacterService
import com.dooques.fightingflow.exceptions.FightingFlowExceptions
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/characters")
class CharacterController(
    private val characterService: CharacterService,
    private val restTemplate: RestTemplate,
) {

    init {
        println("""
            ******************************************** 
            Character Controller Initialized
            ********************************************
            """.trimIndent())
    }

    @GetMapping
    fun getCharacters(
        @RequestParam("id", required = false) id: Long? = null,
        @RequestParam("name", required = false) name: String? = null,
        @RequestParam("game", required = false) game: String? = null,
        @RequestParam("custom", required = false) custom: Boolean = false,
    ): Any {
        return if (custom) {
            characterService.getCustomCharacters()
        } else if (id != null) {
            characterService.getCharacterByName(id.toString())
        } else if (name != null) {
            if (game != null) {
                characterService.getCharacterByNameAndGame(name, game)
            } else {
                characterService.getCharacterByName(name)
            }
        } else if (game != null) {
            characterService.getCharactersByGame(game)
        } else {
            characterService.getAllCharacters()
        }
    }

    @PostMapping
    fun postCharacters(
        @Valid @RequestBody characterDto: CharacterDto
    ): CharacterDto {
        println("Posting Combo: $characterDto")
        return characterService.saveCharacter(characterDto)
    }

    @PutMapping
    fun putCharacters(
        @Valid @RequestBody characterDto: CharacterDto
    ): CharacterDto {
        val characterId = characterDto.id ?: throw Exception("Character ID not found")
        val originalCharacter = characterService.getCharacterById(characterId)

       return if (originalCharacter.name == characterDto.name) {
           characterService.updateCharacter(characterDto)
       } else {
           throw FightingFlowExceptions.Character.InvalidCharacterException(
               characterDto.name,
               emptyMap()
           )
       }
    }

    @DeleteMapping("/{id}")
    fun deleteCharacters(id: Long) {
        characterService.deleteCharacter(id)
    }
}