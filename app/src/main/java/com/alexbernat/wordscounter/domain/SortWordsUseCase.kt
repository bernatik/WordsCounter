package com.alexbernat.wordscounter.domain

import com.alexbernat.wordscounter.ui.model.SortingOption
import com.alexbernat.wordscounter.domain.model.Word

class SortWordsUseCase {

    fun execute(initial: List<Word>, option: SortingOption): List<Word> {
        return when (option) {
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
    }
}