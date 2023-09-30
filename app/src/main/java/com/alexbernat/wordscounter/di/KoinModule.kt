package com.alexbernat.wordscounter.di

import android.content.res.AssetManager
import com.alexbernat.wordscounter.data.AssetsTextRepository
import com.alexbernat.wordscounter.data.InMemoryWordsRepository
import com.alexbernat.wordscounter.domain.CalculateWordsUseCase
import com.alexbernat.wordscounter.domain.GetSortedWordsUseCase
import com.alexbernat.wordscounter.domain.TextRepository
import com.alexbernat.wordscounter.domain.WordsRepository
import com.alexbernat.wordscounter.ui.MainFragmentViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TextRepository> {
        AssetsTextRepository(
            assets = get()
        )
    }
    single<AssetManager> {
        androidApplication().assets
    }
    single<WordsRepository> {
        InMemoryWordsRepository()
    }
    single {
        CalculateWordsUseCase(
            textRepository = get(),
            wordsRepository = get()
        )
    }
    single {
        GetSortedWordsUseCase(
            wordsRepository = get()
        )
    }
    viewModel {
        MainFragmentViewModel(
            calculateWordsUseCase = get(),
            getSortedWordsUseCase = get()
        )
    }
}