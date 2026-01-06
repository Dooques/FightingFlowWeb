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
    fun postMoves(
        @Valid @RequestBody moveData: Any
    ): Any {
        println("Posting Move: $moveData")
        return when (moveData) {
            is MoveDto -> {
                println("********************************************\n" +
                        "       Posting Move: $moveData\n")
                moveService.postMove(moveData)
            }
            is List<*> -> {
                println("******************************************** \n" +
                        "       Posting Moves: $moveData\n" +
                        "********************************************")
                val moves: List<MoveDto> =
                    objectMapper.convertValue(from = moveData)
                moves.map {
                    println(it.toString())
                    val move = moveService.postMove(
                        moveDto = it,
                        postMultiple =  true
                    )
                    move
                }
            }
            else -> {
                throw MoveExceptions.InvalidMoveException(
                    0, mapOf("Invalid Move Data" to moveData))
            }
        }
    }

    @PutMapping
    fun putMoves(
        @RequestBody moveDto: MoveDto
    ): MoveDto {
        return moveService.updateMove(moveDto)
    }

    @DeleteMapping
    fun deleteMoves(
        @RequestParam name: String? = null,
        @RequestParam deleteAll: Boolean? = null
    ) {
        if (deleteAll != null) {
            moveService.deleteAllMoves()
        } else if (name != null) {
            moveService.deleteMove(name)
        } else {
            throw MoveExceptions.DeleteFunctionFailedException(
                "No ID or Delete All Flag Provided."
            )
        }
    }
}