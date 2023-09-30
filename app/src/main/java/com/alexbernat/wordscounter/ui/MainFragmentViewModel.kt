package com.alexbernat.wordscounter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexbernat.wordscounter.domain.CalculateWordsUseCase
import com.alexbernat.wordscounter.domain.GetSortedWordsUseCase
import com.alexbernat.wordscounter.domain.model.SortingOption
import com.alexbernat.wordscounter.domain.model.UseCaseOutput
import com.alexbernat.wordscounter.ui.model.PresentationError
import com.alexbernat.wordscounter.ui.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainFragmentViewModel(
    private val calculateWordsUseCase: CalculateWordsUseCase,
    private val getSortedWordsUseCase: GetSortedWordsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), UiState.Idle)

    fun calculateWordsCount() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = when (val calculateResult = calculateWordsUseCase.execute()) {
                is UseCaseOutput.Success -> {
                    when (val sortedWords = getSortedWordsUseCase.execute(SortingOption.DEFAULT)) {
                        is UseCaseOutput.Success -> {
                            UiState.Data(
                                result = sortedWords.value,
                                sortingOption = SortingOption.DEFAULT
                            )
                        }

                        is UseCaseOutput.Error -> {
                            UiState.Error(PresentationError.Unknown)
                        }
                    }
                }

                is UseCaseOutput.Error -> {
                    UiState.Error(calculateResult.exception.toPresentationError())
                }
            }
        }
    }

    fun applySortingOption(option: SortingOption) {
        _uiState.value.let {
            if (it is UiState.Data && it.sortingOption == option) return
        }
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when (val sortedWordsResult = getSortedWordsUseCase.execute(option)) {
                is UseCaseOutput.Success -> {
                    _uiState.value =
                        UiState.Data(result = sortedWordsResult.value, sortingOption = option)
                }

                is UseCaseOutput.Error -> {
                    _uiState.value = UiState.Error(PresentationError.Unknown)
                }
            }

        }
    }

}
