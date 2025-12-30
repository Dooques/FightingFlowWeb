package com.dooques.fightingflow.exceptions.user

import com.dooques.fightingflow.exceptions.FightingFlowExceptions.InvalidItemException
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.ItemAlreadyExists
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.ItemFunctionFailedException
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.NoItemFoundByNameException
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.NoItemFoundException
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.NoItemsFoundException

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