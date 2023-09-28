package com.alexbernat.wordscounter.domain

import com.alexbernat.wordscounter.domain.model.Word
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CalculateWordsUseCase(
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(text: String): List<Word> = withContext(dispatcher) {
        val wordsMap = hashMapOf<String, Int>()
        val chars = text.toCharArray()
        val charsSequence = StringBuilder()

        fun countWord() {
            val word = charsSequence.toString()
            val count = wordsMap.getOrDefault(word, 0)
            wordsMap[word] = count + 1
            charsSequence.clear()
        }

        var i = 0
        while (i < chars.size) {
            val char = chars[i]
            if (char.isLetter() ||
                char == '’' ||
                (char == '-' && charsSequence.isNotEmpty())
            ) {
                charsSequence.append(char.lowercaseChar())
            } else if (charsSequence.isNotEmpty()) {
                countWord()
            }
            i++
        }
        if (charsSequence.isNotEmpty()) {
            countWord()
        }
        wordsMap.map {
            Word(name = it.key, frequency = it.value)
        }
    }

}