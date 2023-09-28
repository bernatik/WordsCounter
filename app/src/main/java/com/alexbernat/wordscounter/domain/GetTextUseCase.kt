package com.alexbernat.wordscounter.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetTextUseCase(
    private val textRepository: TextRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(): String = withContext(dispatcher) {
        textRepository.loadText()
    }
}