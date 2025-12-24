package com.dooques.fightingflow.controllers

import com.dooques.fightingflow.data.dto.MoveDto
import com.dooques.fightingflow.data.service.CharacterService
import com.dooques.fightingflow.data.service.MoveService
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.Move
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
    ): List<MoveDto> {
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
        val moveId = moveDto.id ?: throw Move.NoMoveFoundException(0)
        val originalMove = moveService.getMoveById(moveId).first()

        if (originalMove == moveDto) {
            throw Move.InvalidMoveException(
                moveDto.id,
                mapOf("Invalid Change" to "No changes detected")
            )
        }

        return if (originalMove.id == moveId) {
            moveService.updateMove(
                moveDto.copy(
                    name = moveDto.name?.takeIf { it != originalMove.name } ?: originalMove.name,
                    notation = moveDto.notation?.takeIf { it != originalMove.notation } ?: originalMove.notation,
                    moveType = moveDto.moveType?.takeIf { it != originalMove.moveType } ?: originalMove.moveType,
                )
            )
        } else throw Move.InvalidMoveException(
            moveDto.id,
            mapOf("Invalid Change" to "No Move Exists with that ID")
        )
    }

    @DeleteMapping("/{id}")
    fun deleteMoves(@PathVariable id: Long) {
        moveService.deleteMove(id)
    }
}