package com.example.pokemontest.core.execution

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ExecutionThread @Inject constructor() : CoroutineExecutionThread {

    override fun uiThread(): CoroutineDispatcher = Dispatchers.Main

    override fun ioThread(): CoroutineDispatcher = Dispatchers.IO
}