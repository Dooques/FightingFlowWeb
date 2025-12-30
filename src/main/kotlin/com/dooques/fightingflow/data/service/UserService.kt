package com.dooques.fightingflow.data.service

import com.dooques.fightingflow.data.dto.UserDto
import com.dooques.fightingflow.data.dto.toEntity
import com.dooques.fightingflow.data.entities.toDto
import com.dooques.fightingflow.data.repository.ComboRepository
import com.dooques.fightingflow.data.repository.UserRepository
import com.dooques.fightingflow.exceptions.FightingFlowExceptions
import com.dooques.fightingflow.exceptions.combo.ComboExceptions
import com.dooques.fightingflow.exceptions.user.UserExceptions
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
            .ifEmpty { throw UserExceptions.NoUsersFoundException() }

    fun getUserById(id: Long): UserDto =
        userRepository.findById(id)
            .map { it.toDto() }
            .orElseThrow { UserExceptions.NoUserFoundException(id) }

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
                throw UserExceptions.UserAlreadyExists()
            }
        println("Function executed unsuccessfully, throwing exception.")
        throw UserExceptions.PostFunctionFailedException("Failed without reason")
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
                characterList = userDto.characterList?.takeIf { it != originalUser.characterList } ?: originalUser.characterList
            )

        }
            .onSuccess {
                return userRepository
                    .save(updatedUser.toEntity())
                    .toDto()
            }
            .onFailure { result ->
                throw ComboExceptions.PutFunctionFailedException(
                    result.message ?: "Failed without reason."
                )
            }
        throw ComboExceptions.PutFunctionFailedException("Failed without reason")
    }

    fun deleteUser(userId: Long) {
        runCatching {
            val userToDelete = getUserById(userId).toEntity()
            userRepository.delete(userToDelete)
        }
            .onFailure { throw UserExceptions.DeleteFunctionFailedException("Failed without reason.") }
    }
}