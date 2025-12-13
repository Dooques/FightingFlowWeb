package com.dooques.fightingflow.data.repository

import com.dooques.fightingflow.data.entities.UserEntity
import org.apache.catalina.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository: JpaRepository<UserEntity, Long>