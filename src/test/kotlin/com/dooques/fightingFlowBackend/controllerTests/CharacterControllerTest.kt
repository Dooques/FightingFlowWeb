package com.dooques.fightingFlowBackend.controllerTests

import com.dooques.fightingFlowBackend.controllers.CharacterController
import com.dooques.fightingFlowBackend.data.dto.CharacterDto
import com.dooques.fightingFlowBackend.data.service.CharacterService
import com.dooques.fightingFlowBackend.exceptions.character.CharacterExceptions
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

@WebMvcTest(CharacterController::class)
class CharacterControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var characterService: CharacterService

    @MockitoBean
    private lateinit var restTemplate: RestTemplate

    private val objectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())

    @Test
    fun `getCharacters should call getCustomCharacters when custom is true`() {
        whenever(characterService.getCustomCharacters()).thenReturn(emptyList())

        mockMvc.perform(get("/characters").param("custom", "true"))
            .andExpect(status().isOk)
        verify(characterService).getCustomCharacters()
    }

    @Test
    fun `getCharacters should return 404 when name not found`() {
        val name = "Unknown"
        whenever(characterService.getCharacterByName(name))
            .thenThrow(CharacterExceptions.NoCharacterFoundByNameException(name))

        mockMvc.perform(get("/characters")
            .param("name", name))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.errorCode").value("CHARACTER_NOT_FOUND_BY_NAME"))
    }

    @Test
    fun `postCharacters should return 400 when game name is too short`() {
        val invalidCharacter = CharacterDto(name = "S", game = "SF")

        whenever(characterService.saveCharacter(any()))
            .thenThrow(CharacterExceptions.InvalidCharacterException(
                id = invalidCharacter.id ?: 0, emptyMap())
            )

        mockMvc.perform(post("/characters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidCharacter)))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `putCharacters should return 200 on success`() {
        val characterDto = CharacterDto(id = 1, name = "Ryu", game = "Street Fighter")
        whenever(characterService.updateCharacter(any()))
            .thenReturn(characterDto)

        mockMvc.perform(put("/characters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(characterDto)))
            .andExpect(status().isOk)
    }
}