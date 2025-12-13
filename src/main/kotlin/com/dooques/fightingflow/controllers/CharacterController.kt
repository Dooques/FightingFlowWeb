package com.dooques.fightingflow.controllers

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/characters")
class CharacterController {

    @GetMapping
    fun getCombos() {}

    @PostMapping
    fun postCombos() {}

    @PutMapping
    fun putCombos() {}

    @DeleteMapping("/{id}")
    fun deleteCombos(id: UUID) {}
}