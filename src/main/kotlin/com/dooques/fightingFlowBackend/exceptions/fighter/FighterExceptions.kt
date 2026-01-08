package com.dooques.fightingFlowBackend.exceptions.fighter

import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions

object FighterExceptions {
    class InvalidFighterException(
        id: Long, problems: Map<String, Any>,
    ) : FightingFlowExceptions.InvalidItemException("character", id, problems)

    class NoFighterFoundException(id: Long) :
        FightingFlowExceptions.NoItemFoundException("character", id)

    class NoFighterFoundByNameException(name: String) :
        FightingFlowExceptions.NoItemFoundByNameException("character", name)

    class NoFightersFoundException :
        FightingFlowExceptions.NoItemsFoundException("character")

    class FighterAlreadyExistsException :
        FightingFlowExceptions.ItemAlreadyExists("character")

    // Function Exceptions

    class PostFunctionFailedException(message: String) :
        FightingFlowExceptions.ItemFunctionFailedException("Move", "Post", message)

    class PutFunctionFailedException(message: String) :
        FightingFlowExceptions.ItemFunctionFailedException("Move", "Put", message)

    class DeleteFunctionFailedException(message: String) :
        FightingFlowExceptions.ItemFunctionFailedException("Move", "Delete", message)
}