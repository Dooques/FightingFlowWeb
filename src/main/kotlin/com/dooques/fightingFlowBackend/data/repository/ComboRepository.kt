package com.dooques.fightingFlowBackend.data.repository

import com.dooques.fightingFlowBackend.data.entities.ComboEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ComboRepository: JpaRepository<ComboEntity, Long> {
    fun findByCharacter(character: String): List<ComboEntity>
    fun getAllCombosByCreator(creator: String): List<ComboEntity>
    fun findAllByTitle(title: String): List<ComboEntity>
}