package com.dooques.fightingflow.data.entities

import com.dooques.fightingflow.data.dto.CharacterDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant

@Entity
@Table(name = "characters")
data class CharacterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    val name: String = "",
    @CreationTimestamp
    var dateCreated: Instant? = Instant.now(),
    val imageId: Int? = 0,
    val imageUri: String? = null,
    val fightingStyle: String = "",
    val combosById: List<String>? = emptyList(),
    val game: String = "",
    val controlType: String = "",
    val numpadNotation: Boolean? = false,
    val uniqueMoves: String? = null,
    val mutable: Boolean? = false
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