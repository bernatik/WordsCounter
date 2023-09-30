package com.alexbernat.wordscounter.data

import android.content.res.AssetManager
import com.alexbernat.wordscounter.data.model.toDomainException
import com.alexbernat.wordscounter.domain.TextRepository

class AssetsTextRepository(
    private val assets: AssetManager
) : TextRepository {

    override suspend fun loadText(): String {
        return try {
            assets.open(FILE_NAME).bufferedReader().use {
                it.readText()
            }
        } catch (e: Exception) {
            throw e.toDomainException()
        }
    }

    companion object {
        private const val FILE_NAME = "Romeo-and-Juliet.txt"
    }
}