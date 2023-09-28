package com.alexbernat.wordscounter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexbernat.wordscounter.domain.CalculateWordsUseCase
import com.alexbernat.wordscounter.domain.GetTextUseCase
import com.alexbernat.wordscounter.domain.SortWordsUseCase
import com.alexbernat.wordscounter.ui.model.CalculationResult
import com.alexbernat.wordscounter.ui.model.SortingOption
import com.alexbernat.wordscounter.ui.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainFragmentViewModel(
    private val getTextUseCase: GetTextUseCase,
    private val calculateWordsUseCase: CalculateWordsUseCase,
    private val sortWordsUseCase: SortWordsUseCase
) : ViewModel() {

    private val _sortingOption = MutableStateFlow<SortingOption>(SortingOption.FrequencyDesc)
    private val _calculationResult = MutableStateFlow<CalculationResult?>(null)
    val uiState: StateFlow<UiState> = combine(_calculationResult, _sortingOption) { result, sort ->
        produceState(result, sort)
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), UiState())

    fun calculateWordsCount() {
        viewModelScope.launch {
            _calculationResult.value = try {
                val text = getTextUseCase.execute()
                val wordsList = calculateWordsUseCase.execute(text)
                CalculationResult.Success(wordsList)
            } catch (e: Exception) {
                CalculationResult.Error(message = e.message ?: e.javaClass.name)
            }
        }
    }

    fun applySortingOption(option: SortingOption) {
        _sortingOption.value = option
    }

    private fun produceState(result: CalculationResult?, sort: SortingOption): UiState {
        return if (result is CalculationResult.Success) {
            val sortedData = sortWordsUseCase.execute(result.data, sort)
            UiState(result = result.copy(data = sortedData), sortingOption = sort)
        } else {
            UiState(result = result, sortingOption = sort)
        }
    }

}
