package com.dooques.fightingflow.data.dto

import com.dooques.fightingflow.data.entities.ComboEntity
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.Range
import java.time.Instant

data class ComboDto(
    val id: Long? = 0,

    @field:Length(
        min = 3,
        max = 40,
        message = "Title must be between 5 and 40 characters long")
    val title: String? = "",

    val character: String? = "",

    @field:Range(
        min = 1,
        max = 9999,
        message = "Damage must be between 1 and 9999"
    )
    val damage: Int? = 0,

    @field:Pattern(
        regexp = "^[a-zA-Z0-9_-]*$",
        message = "Creator can only contain alphanumeric characters, hyphens and underscores"
    )
    @NotEmpty(message = "Creator cannot be empty")
    var creator: String = "",
    var dateCreated: Instant? = Instant.now(),

    @field:Range(
        min = 0,
        max = 5,
        message = "Difficulty must be between 0 and 5"
    )
    val difficulty: Float? = 0f,
    val likes: Int? = 0,
    val tags: List<String>? = null,
    val private: Boolean? = false,

    @Length(
        min = 5,
        message = "Game must be at least 5 characters long"
    )
    val game: String? = "",

    @Length(
        min = 1,
        message = "Control type must not be empty"
    )
    var controlType: String? = "",

    @Size(min = 2, message = "Combo must have at least 2 moves")
    val moves: List<String>? = listOf(),
)

fun ComboDto.toEntity() = ComboEntity(
    id = id ?: 0,
    title = title ?: "",
    character = character ?: "",
    damage = damage ?: 0,
    creator = creator,
    dateCreated = dateCreated,
    difficulty = difficulty ?: 0f,
    likes = likes ?: 0,
    tags = tags ?: emptyList(),
    private = private ?: false,
    game = game ?: "",
    controlType = controlType ?: "",
    moves = moves ?: emptyList(),
)