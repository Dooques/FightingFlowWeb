package com.dooques.fightingFlowBackend.exceptions.combo

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ComboExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onNoCombosFound(e: ComboExceptions.NoCombosFoundException) = mapOf(
        "errorCode" to "NO_COMBOS_FOUND",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onNoComboFound(e: ComboExceptions.NoComboFoundException) = mapOf(
        "errorCode" to "NO_COMBO_FOUND",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onComboAlreadyExists(e: ComboExceptions.ComboAlreadyExistsException) = mapOf(
        "errorCode" to "COMBO_ALREADY_EXISTS",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun onComboActionFailed(e: ComboExceptions.PostFunctionFailedException) = mapOf(
        "errorCode" to "COMBO_ACTION_FAILED",
        "message" to e.message
    )
}