package com.dooques.fightingflow.exceptions.move

import com.dooques.fightingflow.exceptions.FightingFlowExceptions.InvalidItemException
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.ItemAlreadyExists
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.ItemFunctionFailedException
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.NoItemFoundException
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.NoItemsFoundException

object MoveExceptions {
    class InvalidMoveException(
        id: Long, problems: Map<String, Any>,
    ) : InvalidItemException("move", id, problems)

    class NoMoveFoundException(id: Long) : NoItemFoundException("move", id)

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