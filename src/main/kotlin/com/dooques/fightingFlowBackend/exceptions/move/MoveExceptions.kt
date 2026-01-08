package com.dooques.fightingFlowBackend.exceptions.move

import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions.InvalidItemException
import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions.ItemAlreadyExists
import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions.ItemFunctionFailedException
import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions.NoItemFoundException
import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions.NoItemsFoundException

object MoveExceptions {
    class InvalidMoveException(
        name: String, problems: Map<String, Any>,
    ) : InvalidItemException("move", name, problems)

    class NoMoveFoundException(name: String) : NoItemFoundException("move", name)

    class NoMovesFoundException : NoItemsFoundException("move")

    class MoveAlreadyExistsException : ItemAlreadyExists("move")

    // Function Exceptions

    class PostFunctionFailedException(message: String) :
        ItemFunctionFailedException("Move", "Post", message)

    class PutFunctionFailedException(message: String) :
        ItemFunctionFailedException("Move", "Put", message)

    class DeleteFunctionFailedException(message: String) :
        ItemFunctionFailedException("Move", "Delete", message)
}