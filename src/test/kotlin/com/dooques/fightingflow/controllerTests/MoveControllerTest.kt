package com.dooques.fightingflow.controllerTests

import com.dooques.fightingflow.controllers.MoveController
import com.dooques.fightingflow.data.dto.MoveDto
import com.dooques.fightingflow.data.service.MoveService
import com.dooques.fightingflow.exceptions.FightingFlowExceptions
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
import org.springframework.web.client.RestTemplate

@WebMvcTest(MoveController::class)
class MoveControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var moveService: MoveService

    @MockitoBean
    private lateinit var restTemplate: RestTemplate

    private val objectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())

    @Test
    fun `getMoves should return character moves when character param provided`() {
        val character = "Jin"
        whenever(moveService.getAllMovesByCharacter(character)).thenReturn(listOf(MoveDto(name = "Electric", character = character)))

        mockMvc.perform(get("/moves").param("character", character))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value("Electric"))
    }

    @Test
    fun `getMoves should return 404 when no moves found for game`() {
        val game = "NonExistentGame"
        whenever(moveService.getAllMovesByGame(game)).thenThrow(FightingFlowExceptions.NoItemsFoundException("move"))

        mockMvc.perform(get("/moves").param("game", game))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `postMoves should return 400 when notation is too long`() {
        val invalidMove = MoveDto(name = "Punch", notation = "VERYLONGNOTATION", character = "Ryu")

        mockMvc.perform(post("/moves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidMove)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.notation").exists())
    }

    @Test
    fun `deleteMoves should return 200 on success`() {
        val id = 5L
        mockMvc.perform(delete("/moves/$id"))
            .andExpect(status().isOk)
        verify(moveService).deleteMove(id)
    }
}