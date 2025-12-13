package com.dooques.fightingflow.data.entities

import com.dooques.fightingflow.data.dto.UserEntry
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    val username: String = "",
    val email: String = "",
    var profilePic: String = "",
    var dateCreated: String = "",
    var dob: String = "",
    var name: String = "",
    var likedCombos: List<String> = emptyList(),
    var characterList: List<String> = emptyList()
)

fun UserEntity.toDto() = UserEntry(
    id = id,
    username = username,
    email = email,
    profilePic = profilePic,
    dateCreated = dateCreated,
    dob = dob,
    name = name,
    likedCombos = likedCombos,
    characterList = characterList
)