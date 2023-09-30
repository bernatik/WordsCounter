package com.alexbernat.wordscounter.domain

import com.alexbernat.wordscounter.domain.model.SortingOption
import com.alexbernat.wordscounter.domain.model.UseCaseOutput
import com.alexbernat.wordscounter.domain.model.Word
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetSortedWordsUseCase(
    private val wordsRepository: WordsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun execute(option: SortingOption): UseCaseOutput<List<Word>> =
        withContext(dispatcher) {
            val initial = wordsRepository.getWords()
            val sortedList = when (option) {
                SortingOption.FrequencyDesc -> {
                    initial.sortedByDescending { it.frequency }
                }

                SortingOption.Alphabetical -> {
                    initial.sortedBy { it.name }
                }

                SortingOption.Length -> {
                    initial.sortedBy { it.name.length }
                }

                SortingOption.Frequency -> {
                    initial.sortedBy { it.frequency }
                }

                SortingOption.AlphabeticalDesc -> {
                    initial.sortedByDescending { it.name }
                }

                SortingOption.LengthDesc -> {
                    initial.sortedByDescending { it.name.length }
                }
            }
            UseCaseOutput.Success(sortedList)
        }
}