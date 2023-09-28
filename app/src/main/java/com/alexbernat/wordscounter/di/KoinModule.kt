package com.alexbernat.wordscounter.di

import android.content.res.AssetManager
import com.alexbernat.wordscounter.data.AssetsTextRepository
import com.alexbernat.wordscounter.domain.CalculateWordsUseCase
import com.alexbernat.wordscounter.domain.GetTextUseCase
import com.alexbernat.wordscounter.domain.SortWordsUseCase
import com.alexbernat.wordscounter.domain.TextRepository
import com.alexbernat.wordscounter.ui.MainFragmentViewModel
import kotlinx.coroutines.Dispatchers
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
    single {
        GetTextUseCase(
            textRepository = get(),
            dispatcher = Dispatchers.IO
        )
    }
    single {
        CalculateWordsUseCase(
            dispatcher = Dispatchers.Default
        )
    }
    single {
        SortWordsUseCase()
    }
    viewModel {
        MainFragmentViewModel(
            getTextUseCase = get(),
            calculateWordsUseCase = get(),
            sortWordsUseCase = get()
        )
    }
}