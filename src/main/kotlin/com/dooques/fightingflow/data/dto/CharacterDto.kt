package com.dooques.fightingflow.data.dto

import com.dooques.fightingflow.data.entities.CharacterEntity
import jakarta.persistence.Column
import org.hibernate.validator.constraints.Length
import java.time.Instant

data class CharacterDto(
    val id: Long? = 0,

    @field:Length(min = 3, max = 20, message = "Name must be between 3 and 20 characters long")
    val name: String = "",

    val dateCreated: Instant? = Instant.now(),
    val imageId: Int? = 0,
    val imageUri: String? = "",

    @field:Length(min = 3, max = 50, message = "Fighting style must be between 3 and 50 characters long")
    val fightingStyle: String? = "",

    val combosById: List<String>? = emptyList(),

    @field:Length(min = 5, message = "Game must be between 3 and 20 characters long")
    val game: String? = "",

    @field:Length(min = 1, message = "Control type must not be empty")
    val controlType: String? = "",

    val numpadNotation: Boolean? = false,
    val uniqueMoves: String? = "",
    val mutable: Boolean? = false
)

fun CharacterDto.toEntity() = CharacterEntity(
    id = id,
    name = name,
    imageId = imageId ?: 0,
    imageUri = imageUri ?: "",
    fightingStyle = fightingStyle ?: "",
    combosById = combosById ?: emptyList(),
    game = game ?: "",
    controlType = controlType ?: "",
    numpadNotation = numpadNotation ?: false,
    uniqueMoves = uniqueMoves ?: "",
    mutable = mutable ?: false
)