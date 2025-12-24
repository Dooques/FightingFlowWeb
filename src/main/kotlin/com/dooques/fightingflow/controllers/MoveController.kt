package com.dooques.fightingflow.controllers

import com.dooques.fightingflow.data.dto.MoveDto
import com.dooques.fightingflow.data.service.MoveService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.util.UUID

@RestController
@RequestMapping("/moves")
class MoveController(
    private val moveService: MoveService,
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
    fun getMoves(
        @RequestParam("id", required = false) id: Long? = null,
        @RequestParam("character", required = false) character: String? = null,
        @RequestParam("game", required = false) game: String? = null,
    ): List<MoveDto> {
        return if (id != null) {
            moveService.getMoveById(id)
        } else {
            moveService.getAllMoves()
        }
    }

    @PostMapping
    fun postCombos() {}

    @PutMapping
    fun putCombos() {}

    @DeleteMapping("/{id}")
    fun deleteCombos(id: UUID) {}
}