package com.dooques.fightingFlowBackend.controllers

import com.dooques.fightingFlowBackend.data.dto.ComboDto
import com.dooques.fightingFlowBackend.data.service.ComboService
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
        @RequestParam("title", required = false) title: String? = null,
        @RequestParam("user", required = false) user: String? = null,
        @RequestParam("fighter", required = false) fighter: String? = null,
    ): Any {
        return if (id != null) {
            comboService.getComboById(id)
        } else if (title != null) {
            comboService.getCombosByTitle(title)
        } else if (user != null) {
            comboService.getCombosByUser(user)
        } else if (fighter != null) {
            comboService.getCombosByFighter(fighter)
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
                Combo created by ${comboDto.creator} with fighter ${comboDto.fighter} and moves: ${comboDto.moves}
            **************************************
            """
        )
        return comboService.saveCombo(comboDto)
    }

    @PutMapping
    fun putCombo(
        @RequestBody comboDto: ComboDto
    ): ComboDto {
        return comboService.updateCombo(comboDto)
    }

    @DeleteMapping("/{id}")
    fun deleteCombo(@PathVariable id: Long) {
        comboService.deleteCombo(id)
    }
}