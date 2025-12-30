package com.dooques.fightingflow.exceptions.combo

import com.dooques.fightingflow.exceptions.FightingFlowExceptions.InvalidItemException
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.ItemAlreadyExists
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.ItemFunctionFailedException
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.NoItemFoundByNameException
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.NoItemFoundException
import com.dooques.fightingflow.exceptions.FightingFlowExceptions.NoItemsFoundException

object ComboExceptions {

    class InvalidComboException(
        id: Long, problems: Map<String, Any>,
        ) : InvalidItemException("combo", id, problems)

    class NoComboFoundException(id: Long) :
        NoItemFoundException("combo", id)

    class NoCombosFoundByTitleException(title: String) :
        NoItemFoundByNameException("combo", title)

    class NoCombosFoundException :
        NoItemsFoundException("combo")

    class ComboAlreadyExistsException :
        ItemAlreadyExists("combo")

    // Function Exceptions

    class PostFunctionFailedException(message: String) :
        ItemFunctionFailedException("Combo", "Post", message)

    class PutFunctionFailedException(message: String) :
        ItemFunctionFailedException("Combo", "Put", message)

    class DeleteFunctionFailedException(message: String) :
        ItemFunctionFailedException("Combo", "Delete", message)
}