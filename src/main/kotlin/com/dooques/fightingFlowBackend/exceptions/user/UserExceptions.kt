package com.dooques.fightingFlowBackend.exceptions.user

import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions.InvalidItemException
import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions.ItemAlreadyExists
import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions.ItemFunctionFailedException
import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions.NoItemFoundByNameException
import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions.NoItemFoundException
import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions.NoItemsFoundException

object UserExceptions {
    class InvalidUserException(
        id: Long, problems: Map<String, Any>,
    ) : InvalidItemException("user", id, problems)

    class NoUserFoundException(id: Long) : NoItemFoundException("user", id)

    class NoUserFoundByNameException(name: String) : NoItemFoundByNameException("user", name)

    class NoUsersFoundException : NoItemsFoundException("user")

    class UserAlreadyExists : ItemAlreadyExists("user")

    // Function Exceptions
    class PostFunctionFailedException(message: String = "") :
        ItemFunctionFailedException("User", "Post", message)

    class PutFunctionFailedException(message: String) :
        ItemFunctionFailedException("User", "Put", message)

    class DeleteFunctionFailedException(message: String) :
        ItemFunctionFailedException("User", "Delete", message)
}