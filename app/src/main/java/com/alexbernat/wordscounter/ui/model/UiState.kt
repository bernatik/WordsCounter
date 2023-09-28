package com.alexbernat.wordscounter.ui.model

data class UiState(
    val result: CalculationResult? = null,
    val sortingOption: SortingOption = SortingOption.FrequencyDesc
)

