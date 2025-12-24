package com.dooques.fightingflow.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import com.dooques.fightingflow.exceptions.FightingFlowExceptions as Exceptions

@RestControllerAdvice
class FightingFlowExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onUserNotFound(e: Exceptions.User.UserNotFoundException) = mapOf(
        "errorCode" to "USER_NOT_FOUND",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onMoveNotFound(e: Exceptions.Move.MoveNotFoundException) = mapOf(
        "errorCode" to "MOVE_NOT_FOUND",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onCharacterNotFound(e: Exceptions.Character.CharacterNotFoundException) = mapOf(
        "errorCode" to "CHARACTER_NOT_FOUND",
        "message" to e.message
    )

    @ExceptionHandler(
        org.springframework.http.converter.HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun invalidCharacterException(
        e: org.springframework.web.bind.MethodArgumentNotValidException
    ): Map<String, Any> {
        val problems = mutableMapOf<String, String?>()
        e.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as org.springframework.validation.FieldError).field
            val errorMessage = error.defaultMessage
            problems[fieldName] = errorMessage
        }

        return mapOf("errorCode" to "INVALID_CHARACTER", "message" to problems)
    }
}