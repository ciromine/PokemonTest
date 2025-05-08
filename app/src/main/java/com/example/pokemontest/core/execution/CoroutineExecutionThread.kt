package com.example.pokemontest.core.execution

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineExecutionThread {

    fun uiThread(): CoroutineDispatcher

    fun ioThread(): CoroutineDispatcher
}