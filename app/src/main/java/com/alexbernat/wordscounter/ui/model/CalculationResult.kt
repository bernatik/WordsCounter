package com.alexbernat.wordscounter.ui.model

import com.alexbernat.wordscounter.domain.model.Word

sealed class CalculationResult {
    data class Success(val data: List<Word>) : CalculationResult()
    data class Error(val message: String) : CalculationResult()
}