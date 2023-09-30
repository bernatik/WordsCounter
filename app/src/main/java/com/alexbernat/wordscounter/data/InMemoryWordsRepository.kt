package com.alexbernat.wordscounter.data

import com.alexbernat.wordscounter.domain.WordsRepository
import com.alexbernat.wordscounter.domain.model.Word
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class InMemoryWordsRepository : WordsRepository {

    private val wordsList = mutableListOf<Word>()
    private val mutex = Mutex()

    override suspend fun save(words: List<Word>) {
        mutex.withLock {
            wordsList.clear()
            wordsList.addAll(words)
        }
    }

    override suspend fun getWords(): List<Word> {
        return mutex.withLock { wordsList }
    }
}