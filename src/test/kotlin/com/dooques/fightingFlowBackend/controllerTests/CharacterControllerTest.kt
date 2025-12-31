package com.dooques.fightingFlowBackend.controllerTests

import com.dooques.fightingFlowBackend.controllers.FighterController
import com.dooques.fightingFlowBackend.data.dto.FighterDto
import com.dooques.fightingFlowBackend.data.service.FighterService
import com.dooques.fightingFlowBackend.exceptions.character.FighterExceptions
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

@WebMvcTest(FighterController::class)
class FighterControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var fighterService: FighterService

    @MockitoBean
    private lateinit var restTemplate: RestTemplate

    private val objectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())

    @Test
    fun `getFighters should call getCustomFighters when custom is true`() {
        whenever(fighterService.getCustomFighters()).thenReturn(emptyList())

        mockMvc.perform(get("/Fighters").param("custom", "true"))
            .andExpect(status().isOk)
        verify(fighterService).getCustomFighters()
    }

    @Test
    fun `getFighters should return 404 when name not found`() {
        val name = "Unknown"
        whenever(fighterService.getFighterByName(name))
            .thenThrow(FighterExceptions.NoFighterFoundByNameException(name))

        mockMvc.perform(get("/Fighters")
            .param("name", name))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.errorCode").value("Fighter_NOT_FOUND_BY_NAME"))
    }

    @Test
    fun `postFighters should return 400 when game name is too short`() {
        val invalidFighter = FighterDto(name = "S", game = "SF")

        whenever(fighterService.saveFighter(any()))
            .thenThrow(FighterExceptions.InvalidFighterException(
                id = invalidFighter.id ?: 0, emptyMap())
            )

        mockMvc.perform(post("/Fighters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidFighter)))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `putFighters should return 200 on success`() {
        val fighterDto = FighterDto(id = 1, name = "Ryu", game = "Street Fighter")
        whenever(fighterService.updateFighter(any()))
            .thenReturn(fighterDto)

        mockMvc.perform(put("/Fighters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(fighterDto)))
            .andExpect(status().isOk)
    }
}