package com.dooques.fightingFlowBackend.data.dto

import com.dooques.fightingFlowBackend.data.entities.UserEntity
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.URL
import java.time.Instant

data class UserDto(
    var id: Long? = 0,
    @field:Length(min = 5, max = 20, message = "Username must be between 5 and 20 characters long")
    val username: String? = "",
    @field:Email(message = "Invalid email address")
    val email: String? = "",
    @field:URL(message = "Invalid URL")
    val profilePic: String? = "",
    val dateCreated: Instant? = Instant.now(),
    @field:Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Invalid date of birth")
    val dob: String? = "",
    @field:Pattern(regexp = "^[A-Za-z]+$", message = "Name must contain only letters")
    val name: String? = "",
    val likedCombos: List<String>? = emptyList(),
    val characterList: List<String>? = emptyList()
)

fun UserDto.toEntity() = UserEntity(
    id = id ?: 0,
    username = username ?: "",
    email = email ?: "",
    profilePic = profilePic ?: "",
    dateCreated = dateCreated ?: Instant.now(),
    dob = dob ?: "",
    name = name ?: "",
    likedCombos = likedCombos ?: emptyList(),
    characterList = characterList ?: emptyList()
)