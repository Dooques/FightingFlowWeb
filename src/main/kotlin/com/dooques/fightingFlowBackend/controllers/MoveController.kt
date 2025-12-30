package com.dooques.fightingFlowBackend.controllers

import com.dooques.fightingFlowBackend.data.dto.MoveDto
import com.dooques.fightingFlowBackend.data.service.MoveService
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
@RequestMapping("/moves")
class MoveController(
    private val moveService: MoveService,
    private val restTemplate: RestTemplate,
) {

    init {
        println("""
        ********************************************
            Move Controller Initialized
        ********************************************
        """.trimIndent())
    }

    @GetMapping
    fun getMoves(
        @RequestParam("id", required = false) id: Long? = null,
        @RequestParam("character", required = false) character: String? = null,
        @RequestParam("game", required = false) game: String? = null,
    ): Any {
        return if (id != null) {
            moveService.getMoveById(id)
        } else if (character != null) {
            moveService.getAllMovesByCharacter(character)
        } else if (game != null) {
            moveService.getAllMovesByGame(game)
        } else {
            moveService.getAllMoves()
        }
    }

    @PostMapping
    fun postMoves(
        @Valid @RequestBody moveDto: MoveDto
    ): MoveDto {
        println("Posting Move: $moveDto")
        return moveService.postMove(moveDto)
    }

    @PutMapping
    fun putMoves(
        @RequestBody moveDto: MoveDto
    ): MoveDto {
        return moveService.updateMove(moveDto)
    }

    @DeleteMapping("/{id}")
    fun deleteMoves(@PathVariable id: Long) {
        moveService.deleteMove(id)
    }
}