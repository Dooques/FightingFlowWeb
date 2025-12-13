package com.dooques.fightingflow.data.entities

import com.dooques.fightingflow.data.dto.CharacterDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "characters")
data class CharacterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val imageId: Int,
    val imageUri: String? = null,
    val fightingStyle: String,
    val combosById: String = "",
    val game: String,
    val controlType: String,
    val numpadNotation: Boolean,
    val uniqueMoves: String? = null,
    val mutable: Boolean = false
)

fun CharacterEntity.toDto() = CharacterDto(
    id = id,
    name = name,
    imageId = imageId,
    imageUri = imageUri,
    fightingStyle = fightingStyle,
    combosById = combosById,
    game = game,
    controlType = controlType,
    numpadNotation = numpadNotation,
)