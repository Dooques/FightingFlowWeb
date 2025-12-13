package com.dooques.fightingflow.data.repository

import com.dooques.fightingflow.data.entities.ComboEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ComboRepository: JpaRepository<ComboEntity, Long> {
    fun findByCharacter(character: String): List<ComboEntity>
    fun getAllCombosByCreator(creator: String): List<ComboEntity>
}