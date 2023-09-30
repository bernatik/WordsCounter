package com.alexbernat.wordscounter.data

import android.content.res.AssetManager
import com.alexbernat.wordscounter.domain.TextRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AssetsTextRepository(
    private val assets: AssetManager
) : TextRepository {

    private val mutex = Mutex()

    private var cachedString: String? = null

    override suspend fun loadText(): String {
        return cachedString ?: mutex.withLock {
            cachedString ?: assets.open(FILE_NAME).bufferedReader().use {
                it.readText()
            }.also { resultString ->
                cachedString = resultString
            }
        }
    }

    companion object {
        private const val FILE_NAME = "Romeo-and-Juliet.txt"
    }
}