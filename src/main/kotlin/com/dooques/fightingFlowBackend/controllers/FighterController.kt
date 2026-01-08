package com.dooques.fightingFlowBackend.controllers

import com.dooques.fightingFlowBackend.data.dto.FighterDto
import com.dooques.fightingFlowBackend.data.service.FighterService
import com.dooques.fightingFlowBackend.exceptions.fighter.FighterExceptions
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
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue

@RestController
@RequestMapping("/fighters")
class FighterController(
    private val fighterService: FighterService,
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper
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
        @Valid @RequestBody fighterData: FighterDto
    ): Any {

        println(
            """
                ******************************************** 
                    Posting Fighter: $fighterData
                """
        )
        return fighterService.postFighter(fighterData)
    }

    @PostMapping("/bulk")
    fun postFighters(
        @Valid @RequestBody fighterData: List<FighterDto>
    ): List<FighterDto> {
        println(
            """
                ******************************************** 
                    Posting Fighters: $fighterData
                ******************************************** 
                """
        )
        val fighters: List<FighterDto> =
            objectMapper.convertValue(from = fighterData)
        return fighters.map {
            println(it.toString())
            val fighter = fighterService.postFighter(it, true)
            println("Fighter Saved\n")
            fighter
        }
    }

    @PutMapping
    fun putFighter(
        @RequestBody fighterData : FighterDto
    ): FighterDto {
        println(
            """
                ******************************************** 
                    Updating Fighter: $fighterData
                """
        )
        return fighterService.updateFighter(fighterData)
    }
    @PutMapping("/bulk")
    fun putMultipleFighters(
        @RequestBody fighterData : List<FighterDto>
    ): List<FighterDto> {
        return fighterData.map {
            fighterService.updateFighter(it)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteFighter(@PathVariable id: Long) {
        fighterService.deleteFighter(id)
    }

    @DeleteMapping("/all")
    fun deleteAllFighters() {
        fighterService.deleteAllFighters()
    }
}