package com.dooques.fightingflow.controllers

import com.dooques.fightingflow.data.dto.CharacterDto
import com.dooques.fightingflow.data.service.CharacterService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
        println("""
            ******************************************** 
                Posting Character: $characterDto
            ******************************************** 
            """)
        return characterService.saveCharacter(characterDto)
    }

    @PutMapping
    fun putCharacters(
        @RequestBody characterDto : CharacterDto
    ): CharacterDto {
       return characterService.updateCharacter(characterDto)
    }

    @DeleteMapping("/{id}")
    fun deleteCharacters(@PathVariable id: Long) {
        characterService.deleteCharacter(id)
    }
}