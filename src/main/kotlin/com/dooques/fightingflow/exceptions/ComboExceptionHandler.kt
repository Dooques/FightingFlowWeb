package com.dooques.fightingflow.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import com.dooques.fightingflow.exceptions.FightingFlowExceptions as Exceptions

@RestControllerAdvice
class ComboExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onComboValidationFailed(e: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
        val map = mutableMapOf<String, Any>()

        e.bindingResult.fieldErrors.forEach { error ->
            map[error.field] = error.defaultMessage ?: "Validation failed"
        }

        return ResponseEntity(map, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onComboNotFound(e: Exceptions.Combo.ComboNotFoundException) = mapOf(
        "errorCode" to "COMBO_NOT_FOUND",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onNoCombosFound(e: Exceptions.Combo.NoCombosFoundException) = mapOf(
        "errorCode" to "NO_COMBO_FOUND",
        "message" to e.message
    )
}