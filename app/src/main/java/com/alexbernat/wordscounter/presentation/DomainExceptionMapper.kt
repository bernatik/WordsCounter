package com.alexbernat.wordscounter.presentation

import com.alexbernat.wordscounter.domain.model.exceptions.DomainException
import com.alexbernat.wordscounter.presentation.model.PresentationError

fun DomainException.toPresentationError() =
    when (this) {
        is DomainException.Generic -> PresentationError.Unknown
        is DomainException.ReadFile -> PresentationError.ReadFileError
    }