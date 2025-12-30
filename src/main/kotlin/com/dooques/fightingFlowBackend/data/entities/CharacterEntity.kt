package com.dooques.fightingFlowBackend.data.entities

import com.dooques.fightingFlowBackend.data.dto.CharacterDto
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant

@Entity
@Table(name = "characters")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CharacterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    val name: String = "",
    @CreationTimestamp
    var dateCreated: Instant? = Instant.now(),
    @Column(nullable = true)
    val imageId: Int? = 0,
    @Column(nullable = true)
    val imageUri: String? = "",
    val fightingStyle: String = "",
    @Column(nullable = true)
    val combosById: List<String>? = emptyList(),
    val game: String = "",
    val controlType: String = "",
    val numpadNotation: Boolean? = false,
    @Column(nullable = true)
    val uniqueMoves: String? = "",
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