package com.dooques.fightingflow.data.dto

import com.dooques.fightingflow.data.entities.UserEntity

data class UserEntry(
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

fun UserEntry.toEntity() = UserEntity(
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