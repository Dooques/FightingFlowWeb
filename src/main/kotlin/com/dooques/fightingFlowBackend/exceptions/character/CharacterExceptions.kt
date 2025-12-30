package com.dooques.fightingFlowBackend.exceptions.character

import com.dooques.fightingFlowBackend.exceptions.FightingFlowExceptions

object CharacterExceptions {
    class InvalidCharacterException(
        id: Long, problems: Map<String, Any>,
    ) : FightingFlowExceptions.InvalidItemException("character", id, problems)

    class NoCharacterFoundException(id: Long) :
        FightingFlowExceptions.NoItemFoundException("character", id)

    class NoCharacterFoundByNameException(name: String) :
        FightingFlowExceptions.NoItemFoundByNameException("character", name)

    class NoCharactersFoundException :
        FightingFlowExceptions.NoItemsFoundException("character")

    class CharacterAlreadyExistsException :
        FightingFlowExceptions.ItemAlreadyExists("character")

    // Function Exceptions

    class PostFunctionFailedException(message: String) :
        FightingFlowExceptions.ItemFunctionFailedException("Move", "Post", message)

    class PutFunctionFailedException(message: String) :
        FightingFlowExceptions.ItemFunctionFailedException("Move", "Put", message)

    class DeleteFunctionFailedException(message: String) :
        FightingFlowExceptions.ItemFunctionFailedException("Move", "Delete", message)
}