package com.alexbernat.wordscounter.domain.model

enum class SortingOption {
    FrequencyDesc,
    Frequency,
    Alphabetical,
    AlphabeticalDesc,
    Length,
    LengthDesc;

    companion object {
        val DEFAULT = FrequencyDesc
    }
}