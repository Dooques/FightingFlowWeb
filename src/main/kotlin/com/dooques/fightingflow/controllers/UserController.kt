package com.dooques.fightingflow.controllers

import com.dooques.fightingflow.data.dto.ComboDto
import com.dooques.fightingflow.data.dto.UserDto
import com.dooques.fightingflow.data.service.CharacterService
import com.dooques.fightingflow.data.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getCombos(
        @RequestParam("id", required = false) id: Long? = null,
    ): Any {
        println("Getting users")
        return if (id != null) {
            userService.getUserById(id)
        } else {
            userService.getAllUsers()
        }
    }

    @PostMapping
    fun postCombos(
        @Valid @RequestBody userDto: UserDto
    ): UserDto {
        println("Posting User: $userDto")
        return userService.saveUser(userDto)
    }

    @PutMapping
    fun putCombos(
        @Valid @RequestBody userDto: UserDto
    ): UserDto {
        println("Putting User: $userDto")
        return userService.updateUser(userDto)
    }

    @DeleteMapping("/{id}")
    fun deleteCombos(@PathVariable id: Long) {
        println("Deleting User with id: $id")
        userService.deleteUser(id)
    }
}