package com.alexbernat.wordscounter.domain

interface TextRepository {
    suspend fun loadText(): String
}