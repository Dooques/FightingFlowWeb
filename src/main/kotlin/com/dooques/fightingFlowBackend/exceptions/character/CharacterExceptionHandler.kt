package com.dooques.fightingFlowBackend.exceptions.character

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CharacterExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onCharacterNotFound(e: CharacterExceptions.NoCharacterFoundException) = mapOf(
        "errorCode" to "CHARACTER_NOT_FOUND",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onCharacterNotFoundByName(e: CharacterExceptions.NoCharacterFoundByNameException) = mapOf(
        "errorCode" to "CHARACTER_NOT_FOUND_BY_NAME",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onNoCharactersFound(e: CharacterExceptions.NoCharactersFoundException) = mapOf(
        "errorCode" to "NO_CHARACTERS_FOUND",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onInvalidCharacter(e: CharacterExceptions.InvalidCharacterException) = mapOf(
        "errorCode" to "INVALID_CHARACTER_DATA",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onCharacterAlreadyExists(e: CharacterExceptions.CharacterAlreadyExistsException) = mapOf(
        "errorCode" to "CHARACTER_ALREADY_EXISTS",
        "message" to e.message
    )

}