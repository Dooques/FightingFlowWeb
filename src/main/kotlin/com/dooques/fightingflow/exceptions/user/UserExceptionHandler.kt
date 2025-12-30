package com.dooques.fightingflow.exceptions.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onUserNotFound(e: UserExceptions.NoUserFoundException) = mapOf(
        "errorCode" to "USER_NOT_FOUND",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onUsersNotFound(e: UserExceptions.NoUsersFoundException) = mapOf(
        "errorCode" to "USER_NOT_FOUND",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onUserNotFoundByName(e: UserExceptions.NoUserFoundByNameException) = mapOf(
        "errorCode" to "USER_NOT_FOUND_BY_NAME",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onUserAlreadyExists(e: UserExceptions.UserAlreadyExists) = mapOf(
        "errorCode" to "USER_ALREADY_EXISTS",
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onInvalidUser(e: UserExceptions.InvalidUserException) = mapOf(
        "message" to e.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onPostFunctionFailed(e: UserExceptions.PostFunctionFailedException) = mapOf(
        "errorCode" to "USER_ACTION_FAILED",
        "message" to e.message
    )
}