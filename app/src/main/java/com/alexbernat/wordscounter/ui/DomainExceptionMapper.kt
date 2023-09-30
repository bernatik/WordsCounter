package com.alexbernat.wordscounter.ui

import com.alexbernat.wordscounter.domain.model.exceptions.DomainException
import com.alexbernat.wordscounter.ui.model.PresentationError

fun DomainException.toPresentationError() =
    when (this) {
        is DomainException.Generic -> PresentationError.Unknown
        is DomainException.ReadFile -> PresentationError.ReadFileError
    }