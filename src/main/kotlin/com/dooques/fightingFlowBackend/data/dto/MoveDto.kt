package com.dooques.fightingFlowBackend.data.dto

import com.dooques.fightingFlowBackend.data.entities.MoveEntity
import com.dooques.fightingFlowBackend.util.SF6ControlType
import org.hibernate.validator.constraints.Length

data class MoveDto(
    val id: Long? = 0,
    @field:Length(min = 3, max = 40, message = "Name must be between 3 and 40 characters long")
    val name: String? = "",
    @field:Length(min = 1, max = 10, message = "Notation must be between 1 and 10 characters long")
    val notation: String? = "",
    @field:Length(min = 3, max = 10, message = "Move type must be between 3 and 10 characters long")
    val type: String? = "",
    @field:Length(min = 3, max = 20, message = "Character must be between 3 and 20 characters long")
    val fighter: String? = "",
    @field:Length(min = 5, max = 20, message = "Game must be between 5 and 20 characters long")
    val game: String? = null,
    val controlTypeSF: SF6ControlType? = SF6ControlType.Invalid
)

fun MoveDto.toEntity() = MoveEntity(
    id = id ?: 0,
    name = name ?: "",
    notation = notation ?: "",
    type = type ?: "",
    fighter = fighter ?: "",
    game = game ?: "",
    controlTypeSF = controlTypeSF ?: SF6ControlType.Invalid
)