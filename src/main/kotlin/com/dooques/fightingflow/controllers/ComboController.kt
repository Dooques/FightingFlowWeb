package com.dooques.fightingflow.controllers

import com.dooques.fightingflow.data.dto.ComboDto
import com.dooques.fightingflow.data.service.ComboService
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
            Posting Combo: $comboDto
            Combo created by ${comboDto.creator} with character ${comboDto.character} and moves: ${comboDto.moves}
            """
        )
        return comboService.saveCombo(comboDto)
    }

    @PutMapping
    fun putCombo(
        @Valid @RequestBody comboDto: ComboDto
    ) {
        val comboId = comboDto.id ?: throw FightingFlowExceptions.Combo.ComboNotFoundException(comboDto.id ?: 0)
        val combo = comboService.getComboById(comboId)
        println(
            """
            **************************************
            Updating Combo with id ${comboDto.id}
            Original Combo: $combo
            Updated Combo: $comboDto
        """
        )

        val updatedCombo = comboService.updateCombo(comboDto)
        println(
            """Combo Updated: $updatedCombo
            **************************************
        """
        )
    }

    @DeleteMapping("/{id}")
    fun deleteCombo(id: Long) {
        comboService.deleteCombo(id)
    }
}