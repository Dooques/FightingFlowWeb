package com.dooques.fightingFlowBackend.controllers

import com.dooques.fightingFlowBackend.data.dto.MoveDto
import com.dooques.fightingFlowBackend.data.service.MoveService
import com.dooques.fightingFlowBackend.exceptions.move.MoveExceptions
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
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
@RequestMapping("/moves")
class MoveController(
    private val moveService: MoveService,
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper
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
        @RequestParam("name", required = false) name: String? = null,
        @RequestParam("character", required = false) character: String? = null,
        @RequestParam("game", required = false) game: String? = null,
    ): Any {
        return if (name != null) { moveService.getMovesByName(name = name) }
        else if (character != null) { moveService.getAllMovesByFighter(character) }
        else if (game != null) { moveService.getAllMovesByGame(game) }
        else { moveService.getAllMoves() }
    }

    @PostMapping
    fun postMove(
        @Valid @RequestBody moveData: MoveDto
    ): MoveDto {
        println("Posting Move: $moveData")
                println("********************************************\n" +
                        "       Posting Move: $moveData\n")
                return moveService.postMove(moveData)
    }
    @PostMapping("/bulk")
    fun postMoves(
        @Valid @RequestBody moveData: MoveDto
    ): List<MoveDto> {
        println(
            "******************************************** \n" +
                    "       Posting Moves: $moveData\n" +
                    "********************************************")
        val moves: List<MoveDto> =
            objectMapper.convertValue(from = moveData)
        return moves.map { move ->
            println(move.toString())
            val move = moveService.postMove(
                moveDto = move,
                postMultiple =  true
            )
            move
        }
    }

    @PutMapping
    fun putMove(
        @RequestBody moveDto: MoveDto
    ): MoveDto {
        return moveService.updateMove(moveDto)
    }

    @DeleteMapping()
    fun deleteMove(
        @RequestParam name: String? = null,
    ) {
        if (name != null) {
            moveService.deleteMove(name)
        } else {
            throw MoveExceptions.InvalidMoveException("null", mapOf("name" to "null"))
        }

    }

    @DeleteMapping("/all")
    fun deleteMove(
    ) {
        moveService.deleteAllMoves()
    }
}