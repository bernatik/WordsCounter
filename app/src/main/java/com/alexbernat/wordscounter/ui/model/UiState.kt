package com.alexbernat.wordscounter.ui.model

import com.alexbernat.wordscounter.domain.model.SortingOption

data class UiState(
    val result: CalculationResult? = null,
    val sortingOption: SortingOption = SortingOption.FrequencyDesc
)

