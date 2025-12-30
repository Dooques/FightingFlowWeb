package com.dooques.fightingFlowBackend.data.entities

import com.dooques.fightingFlowBackend.data.dto.ComboDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant

@Entity
@Table(name = "combos")
data class ComboEntity (
    @Id
    @GeneratedValue(GenerationType.IDENTITY)
    var id: Long? = 0,
    val title: String = "",
    val character: String = "",
    val damage: Int = 0,
    var creator: String = "",
    @CreationTimestamp
    var dateCreated: Instant? = Instant.now(),
    val difficulty: Float = 0f,
    val likes: Int? = 0,
    val tags: List<String>? = null,
    val private: Boolean = false,
    val game: String = "",
    var controlType: String = "",
    val moves: List<String> = listOf(),
)

fun ComboEntity.toDto() = ComboDto(
    id = id ?: 0,
    title = title,
    character = character,
    damage = damage,
    creator = creator,
    dateCreated = dateCreated ?: Instant.now(),
    difficulty = difficulty,
    likes = likes ?: 0,
    tags = tags ?: emptyList(),
    private = private,
    game = game,
    controlType = controlType,
    moves = moves
)