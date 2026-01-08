package com.dooques.fightingFlowBackend.exceptions.fighter

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class FighterExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onFighterNotFound(e: FighterExceptions.NoFighterFoundException) = mapOf(
        "errorCode" to "CHARACTER_NOT_FOUND",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onFighterNotFoundByName(e: FighterExceptions.NoFighterFoundByNameException) = mapOf(
        "errorCode" to "FIGHTER_NOT_FOUND_BY_NAME",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onNoFightersFound(e: FighterExceptions.NoFightersFoundException) = mapOf(
        "errorCode" to "NO_FIGHTERS_FOUND",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onInvalidFighters(e: FighterExceptions.InvalidFighterException) = mapOf(
        "errorCode" to "INVALID_FIGHTER_DATA",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onFighterAlreadyExists(e: FighterExceptions.FighterAlreadyExistsException) = mapOf(
        "errorCode" to "Fighter_ALREADY_EXISTS",
        "message" to e.message
    )

}