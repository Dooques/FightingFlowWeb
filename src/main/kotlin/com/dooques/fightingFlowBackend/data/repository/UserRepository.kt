package com.dooques.fightingFlowBackend.data.repository

import com.dooques.fightingFlowBackend.data.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserEntity, Long>