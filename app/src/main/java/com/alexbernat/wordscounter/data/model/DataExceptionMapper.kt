package com.alexbernat.wordscounter.data.model

import com.alexbernat.wordscounter.domain.model.exceptions.DomainException
import java.io.IOException

fun Exception.toDomainException(): DomainException =
    when (this) {
        is IOException -> DomainException.ReadFile(this)
        else -> DomainException.Generic(this)
    }