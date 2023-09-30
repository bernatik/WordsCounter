package com.alexbernat.wordscounter.domain

import com.alexbernat.wordscounter.domain.model.UseCaseOutput
import com.alexbernat.wordscounter.domain.model.Word
import com.alexbernat.wordscounter.domain.model.exceptions.DomainException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalculateWordsUseCase(
    private val textRepository: TextRepository,
    private val wordsRepository: WordsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun execute(): UseCaseOutput<Unit> = withContext(dispatcher) {
        try {
            val text = textRepository.loadText()
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
            val wordsList = wordsMap.map {
                Word(name = it.key, frequency = it.value)
            }
            wordsRepository.save(wordsList)
            UseCaseOutput.Success(value = Unit)
        } catch (e: Exception) {
            UseCaseOutput.Error(exception = (e as? DomainException) ?: DomainException.Generic(e))
        }
    }
}