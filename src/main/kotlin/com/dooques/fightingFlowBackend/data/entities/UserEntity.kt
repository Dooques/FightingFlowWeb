package com.dooques.fightingFlowBackend.data.entities

import com.dooques.fightingFlowBackend.data.dto.UserDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    val username: String = "",
    val email: String = "",
    var profilePic: String = "",
    @CreationTimestamp
    var dateCreated: Instant? = Instant.now(),
    var dob: String = "",
    var name: String = "",
    var likedCombos: List<String> = emptyList(),
    var fighterList: List<String> = emptyList()
)

fun UserEntity.toDto() = UserDto(
    id = id,
    username = username,
    email = email,
    profilePic = profilePic,
    dateCreated = dateCreated,
    dob = dob,
    name = name,
    likedCombos = likedCombos,
    fighterList = fighterList
)