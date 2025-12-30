package com.dooques.fightingflow.exceptions.move

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class MoveExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onMoveNotFound(e: MoveExceptions.NoMoveFoundException) = mapOf(
        "errorCode" to "MOVE_NOT_FOUND",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onNoMovesFound(e: MoveExceptions.NoMovesFoundException) = mapOf(
        "errorCode" to "NO_MOVES_FOUND",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onMoveAlreadyExists(e: MoveExceptions.MoveAlreadyExistsException) = mapOf(
        "errorCode" to "MOVE_ALREADY_EXISTS",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onInvalidMove(e: MoveExceptions.InvalidMoveException) = mapOf(
        "errorCode" to "INVALID_MOVE_DATA",
        "message" to e.message
    )
}