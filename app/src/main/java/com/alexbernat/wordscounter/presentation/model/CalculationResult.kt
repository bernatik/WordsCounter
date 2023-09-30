package com.alexbernat.wordscounter.presentation.model

import com.alexbernat.wordscounter.domain.model.Word

sealed class CalculationResult {
    data class Success(val data: List<Word>) : CalculationResult()
    data class Error(val message: String) : CalculationResult()
}