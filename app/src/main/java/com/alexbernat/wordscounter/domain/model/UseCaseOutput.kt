package com.alexbernat.wordscounter.domain.model

import com.alexbernat.wordscounter.domain.model.exceptions.DomainException

sealed class UseCaseOutput<out T> {
    data class Success<T>(val value: T) : UseCaseOutput<T>()
    data class Error(val exception: DomainException) : UseCaseOutput<Nothing>()
}