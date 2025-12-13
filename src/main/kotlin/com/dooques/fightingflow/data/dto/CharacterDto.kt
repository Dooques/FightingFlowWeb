package com.dooques.fightingflow.data.dto

import com.dooques.fightingflow.data.entities.CharacterEntity
import com.dooques.fightingflow.util.ControlType

data class CharacterDto(
    val id: Long = 0,
    val name: String = "",
    val imageId: Int = 0,
    val imageUri: String? = null,
    val fightingStyle: String = "",
    val combosById: String = "",
    val game: String = "",
    val controlType: String = "",
    val numpadNotation: Boolean = false,
    val uniqueMoves: String? = null,
    val mutable: Boolean = false
)

fun CharacterDto.toEntity() = CharacterEntity(
    id = id,
    name = name,
    imageId = imageId,
    imageUri = imageUri,
    fightingStyle = fightingStyle,
    combosById = combosById,
    game = game,
    controlType = controlType,
    numpadNotation = numpadNotation,
    uniqueMoves = uniqueMoves,
)