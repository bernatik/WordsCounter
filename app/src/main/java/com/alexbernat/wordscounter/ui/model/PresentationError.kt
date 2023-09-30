package com.alexbernat.wordscounter.ui.model

import androidx.annotation.StringRes
import com.alexbernat.wordscounter.R

enum class PresentationError(@StringRes val msgResId: Int) {
    ReadFileError(R.string.err_file),
    Unknown(R.string.err_unknown)
}