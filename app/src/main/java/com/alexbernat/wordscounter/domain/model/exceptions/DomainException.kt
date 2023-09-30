package com.alexbernat.wordscounter.domain.model.exceptions

sealed class DomainException(throwable: Throwable) : Exception(throwable) {
    data class ReadFile(val throwable: Throwable) : DomainException(throwable)
    data class Generic(val throwable: Throwable) : DomainException(throwable)
}


