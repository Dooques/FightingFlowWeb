package com.dooques.fightingFlowBackend.controllers

import com.dooques.fightingFlowBackend.data.dto.FighterDto
import com.dooques.fightingFlowBackend.data.service.FighterService
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
@RequestMapping("/fighters")
class FighterController(
    private val fighterService: FighterService,
    private val restTemplate: RestTemplate,
) {

    init {
        println("""
            ******************************************** 
                Fighter Controller Initialized
            ********************************************
            """.trimIndent())
    }

    @GetMapping
    fun getFighter(
        @RequestParam("id", required = false) id: Long? = null,
        @RequestParam("name", required = false) name: String? = null,
        @RequestParam("game", required = false) game: String? = null,
        @RequestParam("custom", required = false) custom: Boolean = false,
    ): Any {
        return if (custom) {
            fighterService.getCustomFighters()
        } else if (id != null) {
            fighterService.getFighterByName(id.toString())
        } else if (name != null) {
            if (game != null) {
                fighterService.getFighterByNameAndGame(name, game)
            } else {
                fighterService.getFighterByName(name)
            }
        } else if (game != null) {
            fighterService.getFightersByGame(game)
        } else {
            fighterService.getAllFighters()
        }
    }

    @PostMapping
    fun postFighter(
        @Valid @RequestBody fighterDto: FighterDto
    ): FighterDto {
        println("""
            ******************************************** 
                Posting Fighter: $fighterDto
            """)
        return fighterService.saveFighter(fighterDto)
    }

    @PutMapping
    fun putFighter(
        @RequestBody fighterDto : FighterDto
    ): FighterDto {
        println("""
            ******************************************** 
                Updating Fighter: $fighterDto
            """)
       return fighterService.updateFighter(fighterDto)
    }

    @DeleteMapping("/{id}")
    fun deleteFighter(@PathVariable id: Long) {
        fighterService.deleteFighter(id)
    }
}