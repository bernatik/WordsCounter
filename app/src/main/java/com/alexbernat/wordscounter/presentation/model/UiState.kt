package com.alexbernat.wordscounter.presentation.model

import com.alexbernat.wordscounter.domain.model.SortingOption
import com.alexbernat.wordscounter.domain.model.Word

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Data(
        val result: List<Word> = emptyList(),
        val sortingOption: SortingOption = SortingOption.DEFAULT
    ) : UiState()

    data class Error(val exception: PresentationError) : UiState()
}

