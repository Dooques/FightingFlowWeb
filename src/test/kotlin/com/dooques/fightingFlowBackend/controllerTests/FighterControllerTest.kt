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
    @MockitoBean
    private lateinit var objectMapper : ObjectMapper

    private val testMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())

    @Test
    fun `getFighters should call getCustomFighters when custom is true`() {
        whenever(fighterService.getCustomFighters())
            .thenReturn(listOf(
                FighterDto(name = "Ryu", game = "Street Fighter"),
            ))

        mockMvc.perform(get("/fighters")
            .param("custom", "true"))
            .andExpect(status().isOk)
        verify(fighterService).getCustomFighters()
    }

    @Test
    fun `getFighters should return 404 when name not found`() {
        val name = "Unknown"
        whenever(fighterService.getFighterByName(name))
            .thenThrow(FighterExceptions.NoFighterFoundByNameException(name))

        mockMvc.perform(get("/fighters")
            .param("name", name))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.errorCode")
                .value("FIGHTER_NOT_FOUND_BY_NAME"))
    }

    @Test
    fun `postFighters should return 400 when game name is too short`() {
        val invalidFighter = FighterDto(id = 1, name = "S", game = "SF")

        whenever(fighterService
            .postFighter(fighterDto = invalidFighter, false))
            .thenThrow(FighterExceptions.InvalidFighterException(
                id = invalidFighter.id ?: 0, emptyMap())
            )

        mockMvc.perform(post("/fighters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testMapper.writeValueAsString(invalidFighter)))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `putFighters should return 200 on success`() {
        val fighterDto = FighterDto(
            name = "Kazuya",
            fightingStyle = "Karate",
            game = "Street Fighter",
            controlType = "Tekken"
        )

        whenever(fighterService.updateFighter(fighterDto))
            .thenReturn(fighterDto)

        mockMvc.perform(put("/fighters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testMapper.writeValueAsString(fighterDto)))
            .andExpect(status().isOk)
    }
}