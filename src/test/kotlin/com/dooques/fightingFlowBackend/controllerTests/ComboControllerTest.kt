package com.dooques.fightingFlowBackend.controllerTests

import com.dooques.fightingFlowBackend.controllers.ComboController
import com.dooques.fightingFlowBackend.data.dto.ComboDto
import com.dooques.fightingFlowBackend.data.service.ComboService
import com.dooques.fightingFlowBackend.exceptions.combo.ComboExceptions
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.isA
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.client.RestTemplate

@WebMvcTest(ComboController::class)
class ComboControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var controller: ComboController

    @MockitoBean
    private lateinit var comboService: ComboService

    @MockitoBean
    private lateinit var restTemplate: RestTemplate

    private val objectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())

    @Test
    fun `comboController runs`() {
        assertThat(controller).isNotNull
    }

    @Test
    fun `getAllCombos should return all combos`() {
        val mockCombos = listOf(
            ComboDto(title = "Starter Combo", game = "SF6"),
            ComboDto(title = "Advanced Combo", game = "Tekken 8")
        )

        whenever(comboService.getAllCombos())
            .thenReturn(mockCombos)

        mockMvc.perform(get("/combos"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].title")
                .value("Starter Combo"))
    }

    @Test
    fun `getCombos with id param should return combo with id`() {
        val id = 1L
        val mockCombo = ComboDto(id = id, title = "Starter Combo", game = "SF6")

        whenever(comboService.getComboById(id))
            .thenReturn(mockCombo)

        mockMvc.perform(get("/combos?id=$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id")
                .value(id))

        verify(comboService).getComboById(id)
    }

    @Test
    fun `getCombos with title param should return combo with title`() {
        val title = "Starter Combo"
        val mockCombo = listOf(
            ComboDto(title = "Advanced Combo", game = "SF6"),
            ComboDto(title = title, game = "SF6")
        )

        whenever(comboService.getCombosByTitle(title))
            .thenReturn(mockCombo.filter { it.title == title })

        mockMvc.perform(get("/combos?title=$title"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].title")
                .value(title))

        verify(comboService).getCombosByTitle(title)
    }

    @Test
    fun `getCombos with creator param should return combo with game`() {
        val creator = "Dooks"
        val mockCombo = listOf(
            ComboDto(game = creator, title = "Advanced Combo", creator = "Other User"),
            ComboDto(game = creator, title = "Starter Combo", creator = creator)
        )

        whenever(comboService.getCombosByUser(creator))
            .thenReturn(mockCombo.filter { it.creator == creator })

        mockMvc.perform(get("/combos?user=$creator"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$")
                .value(hasSize<Any>(1)))
            .andExpect(jsonPath("$[0].game")
                .value(creator))

        verify(comboService).getCombosByUser(creator)
    }

    @Test
    fun `getCombos with character param should return combo with character`() {
        val character = "Kazuya"
        val mockCombo = listOf(
            ComboDto(character = character, title = "Advanced Combo", creator = "Other User"),
            ComboDto(character = "Ryu", title = "Starter Combo", creator = "Other User")
        )

        whenever(comboService.getCombosByCharacter(character))
            .thenReturn(mockCombo.filter { it.character == character })

        mockMvc.perform(get("/combos?character=$character"))
            .andExpect { (status().isOk) }
            .andExpect(jsonPath("$")
                .value(hasSize<Any>(1)))
            .andExpect(jsonPath("$[0].character")
                .value(character))

        verify(comboService).getCombosByCharacter(character)
    }

    @Test
    fun `postCombos should return 400 when name is too short`() {
        val invalidCharacterDto = ComboDto(title = "A", game = "B")

        mockMvc.perform(post("/combos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidCharacterDto)))
            .andExpect(jsonPath("$.title")
                .value("Title must be between 5 and 40 characters long"))
    }

    @Test
    fun `postCombos should return 400 and map containing errors when request is invalid`() {
        val invalidCharacterDto = ComboDto(title = "A", game = "B")

        mockMvc.perform(post("/combos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidCharacterDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath<Map<String, Any>>("$", isA(Map::class.java)))
            .andExpect(jsonPath("$.title").exists())
            .andExpect { jsonPath("$.game").exists() }
    }

    @Test
    fun `putCombos should return 200`() {
        val comboDto = ComboDto(id = 2, title = "Advanced Combo", game = "Tekken")

        mockMvc.perform(put("/combos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(comboDto)))
            .andExpect(status().isOk)
    }

    @Test
    fun `putCombos should return 404 when comboId does not exist`() {
        val invalidComboDto = ComboDto(id = 2, title = "Advanced Combo", game = "Tekken")

        whenever(comboService.updateCombo(any()))
            .thenThrow(ComboExceptions.NoComboFoundException(invalidComboDto.id ?: 0))

        mockMvc.perform(put("/combos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidComboDto)))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.errorCode")
                .value("NO_COMBO_FOUND"))
            .andExpect(jsonPath("$.message")
                .value("No combo with ID 2 could be found")
            )
    }

    @Test
    fun `deleteCombos should return 200`() {
        val comboId = 2L

        mockMvc.perform(delete("/combos/$comboId"))
            .andExpect(status().isOk)

        verify(comboService).deleteCombo(comboId)
    }

    @Test
    fun `deleteCombos should return 404 when comboId does not exist`() {
        val comboId = 2L

        whenever(comboService.deleteCombo(comboId))
            .thenThrow(ComboExceptions.NoComboFoundException(comboId))

        mockMvc.perform(delete("/combos/$comboId"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.errorCode")
                .value("NO_COMBO_FOUND"))
            .andExpect(jsonPath("$.message")
                .value("No combo with ID 2 could be found")
            )
    }
}