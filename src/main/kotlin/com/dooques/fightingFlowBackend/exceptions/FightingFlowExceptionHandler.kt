package com.dooques.fightingFlowBackend.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class FightingFlowExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onInvalidId(e: FightingFlowExceptions.InvalidIdException) = mapOf(
        "errorCode" to "INVALID_ID",
        "message" to e.message
    )

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onMalformedJson(e: Exception) = mapOf(
        "errorCode" to "MALFORMED_JSON",
        "message" to "The request body could not be read correctly."
    )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onValidationFailed(
        e: MethodArgumentNotValidException
    ): ResponseEntity<Map<String, Any>> {
        val map = mutableMapOf<String, Any>()

        e.bindingResult.fieldErrors.forEach { error ->
            map[error.field] = error.defaultMessage ?: "Validation failed"
        }

        return ResponseEntity.badRequest().body(map)
    }

    @ExceptionHandler(java.lang.IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onIllegalArgumentException(e: IllegalArgumentException) = mapOf(
        "errorCode" to "INVALID_ARGUMENT",
        "message" to e.message
    )

    @ExceptionHandler(FightingFlowExceptions.ItemFunctionFailedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onItemFunctionFailed(e: FightingFlowExceptions.ItemFunctionFailedException) = mapOf(
        "errorCode" to "ITEM_ACTION_FAILED",
        "message" to e.message
    )
}