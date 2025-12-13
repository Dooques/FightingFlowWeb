package com.dooques.fightingflow.data.dto

import com.dooques.fightingflow.data.entities.MoveEntity
import com.dooques.fightingflow.util.SF6ControlType

data class MoveDto(
    val id: Long = 0,
    val name: String = "",
    val notation: String = "",
    val moveType: String = "",
    val character: String = "",
    val game: String? = null,
    val controlTypeSF: SF6ControlType? = SF6ControlType.Invalid
)

fun MoveDto.toEntity() = MoveEntity(
    name = name,
    notation = notation,
    moveType = moveType,
    character = character,
    game = game,
    controlTypeSF = controlTypeSF
)