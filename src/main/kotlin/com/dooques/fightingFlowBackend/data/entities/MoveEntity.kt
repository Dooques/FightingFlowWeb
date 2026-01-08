package com.dooques.fightingFlowBackend.data.entities

import com.dooques.fightingFlowBackend.data.dto.MoveDto
import com.dooques.fightingFlowBackend.util.SF6ControlType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant

@Entity
@Table(name = "moves")
data class MoveEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String = "",
    @CreationTimestamp
    val dateCreated: Instant? = Instant.now(),
    val notation: String? = "",
    val type: String? = "",
    val fighter: String? = "",
    val game: String? = null,
    val controlTypeSF: SF6ControlType? = SF6ControlType.Invalid
)

fun MoveEntity.toDto() = MoveDto(
    id = id,
    name = name,
    notation = notation,
    type = type,
    fighter = fighter,
    game = game,
    controlTypeSF = controlTypeSF
)
