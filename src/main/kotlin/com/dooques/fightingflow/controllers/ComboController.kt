package com.dooques.fightingflow.controllers

import com.dooques.fightingflow.data.dto.ComboDto
import com.dooques.fightingflow.data.service.ComboService
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.Combo.InvalidComboException
import com.google.api.client.util.Data.mapOf
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
@RequestMapping("/combos")
class ComboController(
    private val comboService: ComboService,
    private val restTemplate: RestTemplate,
) {
    init {
        println("""
        ********************************************
            Combo Controller Initialized
        ********************************************
        """.trimIndent())
    }

    @GetMapping
    fun getCombos(
        @RequestParam("id", required = false) id: Long? = null,
        @RequestParam("user", required = false) user: String? = null,
        @RequestParam("character", required = false) character: String? = null,
    ): Any {
        return if (user != null) {
            comboService.getCombosByUser(user)
        } else if (id != null) {
            comboService.getComboById(id)
            ComboDto()
        } else if (character != null) {
            comboService.getCombosByCharacter(character)
        } else {
            comboService.getAllCombos()
        }
    }

    @PostMapping
    fun postCombo(
        @Valid @RequestBody comboDto: ComboDto
    ): ComboDto {
        println("""
            **************************************
                Posting Combo: $comboDto
                Combo created by ${comboDto.creator} with character ${comboDto.character} and moves: ${comboDto.moves}
            **************************************
            """
        )
        return comboService.saveCombo(comboDto)
    }

    @PutMapping
    fun putCombo(
        @RequestBody comboDto: ComboDto
    ): ComboDto {
        val comboId = comboDto.id ?: throw InvalidComboException(
            comboDto.id ?: 0,
            mapOf("id" to "Id is null or invalid"),
        )
        val originalCombo = comboService.getComboById(comboId)

        if (originalCombo == comboDto) throw InvalidComboException(
            comboDto.id,
            mapOf("Invalid Change" to "No changes detected"),
        )

        println("""
            **************************************
                Updating Combo with id ${comboDto.id}
                Original Combo: $originalCombo
                Updated Combo: $comboDto
            **************************************
        """
        )

        val updatedCombo = comboDto.copy(
            title = comboDto.title?.takeIf { it != originalCombo.title } ?: originalCombo.title,
            damage = comboDto.damage?.takeIf { it != originalCombo.damage } ?: originalCombo.damage,
            difficulty = comboDto.difficulty?.takeIf { it != originalCombo.difficulty } ?: originalCombo.difficulty,
            tags = comboDto.tags?.takeIf { it != originalCombo.tags } ?: originalCombo.tags,
            game = comboDto.game?.takeIf { it != originalCombo.game } ?: originalCombo.game,
            controlType = comboDto.controlType?.takeIf { it != originalCombo.controlType } ?: originalCombo.controlType,
            moves = comboDto.moves?.takeIf { it != originalCombo.moves } ?: originalCombo.moves,
        )

        return comboService.updateCombo(updatedCombo)
    }

    @DeleteMapping("/{id}")
    fun deleteCombo(@PathVariable id: Long) {
        comboService.deleteCombo(id)
    }
}