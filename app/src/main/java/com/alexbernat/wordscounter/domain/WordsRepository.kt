package com.alexbernat.wordscounter.domain

import com.alexbernat.wordscounter.domain.model.Word

interface WordsRepository {
    suspend fun save(words: List<Word>)
    suspend fun getWords(): List<Word>
}