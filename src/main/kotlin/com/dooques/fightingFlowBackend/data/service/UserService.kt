package com.dooques.fightingFlowBackend.data.service

import com.dooques.fightingFlowBackend.data.dto.UserDto
import com.dooques.fightingFlowBackend.data.dto.toEntity
import com.dooques.fightingFlowBackend.data.entities.toDto
import com.dooques.fightingFlowBackend.data.repository.UserRepository
import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions
import com.dooques.fightingFlowBackend.exceptions.combo.ComboExceptions
import com.dooques.fightingFlowBackend.exceptions.user.UserExceptions
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    /*
    ---------------------------
        Search Functions
    ---------------------------
    */

    fun getAllUsers(): List<UserDto> =
        userRepository.findAll()
            .map { it.toDto() }
            .ifEmpty {
                val error = UserExceptions.NoUsersFoundException()
                println(error.message)
                throw error
            }

    fun getUserById(id: Long): UserDto =
        userRepository.findById(id)
            .map { it.toDto() }
            .orElseThrow {
                val error = UserExceptions.NoUserFoundException(id)
                println(error.message)
                throw error
            }

    /*
    ---------------------------
        Action Functions
    ---------------------------
    */

    fun saveUser(userDto: UserDto): UserDto {
        println("Service Layer")
        println("Saving User: $userDto")
        runCatching {
            userRepository.findById(
                userDto.id ?: throw FightingFlowExceptions.InvalidIdException()
            )
        }
            .onFailure {
                println("No existing users found, saving new user.")
                return userRepository.save(userDto.toEntity()).toDto()
            }
            .onSuccess {
                println("Existing user found, throwing exception.")
                val error = UserExceptions.UserAlreadyExists()
                println(error.message)
                throw error
            }
        println("Function executed unsuccessfully, throwing exception.")
        val error = UserExceptions.PostFunctionFailedException("Failed without reason")
        println(error.message)
        throw error
    }

    fun updateUser(userDto: UserDto): UserDto {
        var updatedUser = UserDto()

        runCatching {
            val userId = userDto.id ?: throw UserExceptions.InvalidUserException(
                0, mapOf("id" to "Id is null or invalid")
            )
            val originalUser = getUserById(userId)

            if (originalUser == userDto) throw UserExceptions.InvalidUserException(
                userId,
                mapOf("Invalid Change" to "No changes detected")
            )
            updatedUser = originalUser.copy(
                username = userDto.username?.takeIf { it != originalUser.username } ?: originalUser.username,
                email = userDto.email?.takeIf { it != originalUser.email } ?: originalUser.email,
                profilePic = userDto.profilePic?.takeIf { it != originalUser.profilePic } ?: originalUser.profilePic,
                name = userDto.name?.takeIf { it != originalUser.name } ?: originalUser.name,
                likedCombos = userDto.likedCombos?.takeIf { it != originalUser.likedCombos } ?: originalUser.likedCombos,
                fighterList = userDto.fighterList?.takeIf { it != originalUser.fighterList } ?: originalUser.fighterList
            )

        }
            .onSuccess {
                return userRepository
                    .save(updatedUser.toEntity())
                    .toDto()
            }
            .onFailure { result ->
                val error = ComboExceptions.PutFunctionFailedException(
                    result.message ?: "Failed without reason."
                )
                println(error.message)
                throw error
            }
        val error = ComboExceptions.PutFunctionFailedException("Failed without reason")
        println(error.message)
        throw error
    }

    fun deleteUser(userId: Long) {
        runCatching {
            val userToDelete = getUserById(userId).toEntity()
            userRepository.delete(userToDelete)
        }
            .onFailure {
                val error = UserExceptions.DeleteFunctionFailedException("Failed without reason.")
                println(error.message)
                throw error
            }
    }
}