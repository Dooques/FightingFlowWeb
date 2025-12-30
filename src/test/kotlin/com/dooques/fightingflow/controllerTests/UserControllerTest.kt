package com.dooques.fightingflow.controllerTests

import com.dooques.fightingflow.controllers.UserController
import com.dooques.fightingflow.data.dto.UserDto
import com.dooques.fightingflow.data.service.UserService
import com.dooques.fightingflow.exceptions.user.UserExceptions
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var userService: UserService

    private val objectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())

    @Test
    fun `getUsers should return all users when no id provided`() {
        whenever(userService.getAllUsers()).thenReturn(listOf(UserDto(username = "Player1")))

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].username").value("Player1"))
    }

    @Test
    fun `getUsers should return 404 when user id not found`() {
        val id = 99L
        whenever(userService.getUserById(id))
            .thenThrow(UserExceptions.NoUserFoundException(id))

        mockMvc.perform(get("/users")
            .param("id", id.toString()))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.errorCode").value("USER_NOT_FOUND"))
    }

    @Test
    fun `postUsers should return 400 when username too short`() {
        val invalidUser = UserDto(username = "abc", email = "test@test.com")

        mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidUser)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.username").exists())
    }

    @Test
    fun `deleteUsers should call service and return 200`() {
        val id = 1L
        mockMvc.perform(delete("/users/$id"))
            .andExpect(status().isOk)
        verify(userService).deleteUser(id)
    }
}