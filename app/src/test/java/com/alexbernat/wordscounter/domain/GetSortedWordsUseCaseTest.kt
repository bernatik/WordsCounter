package com.alexbernat.wordscounter.domain

import com.alexbernat.wordscounter.domain.model.SortingOption
import com.alexbernat.wordscounter.domain.model.UseCaseOutput
import com.alexbernat.wordscounter.domain.model.Word
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub

@RunWith(MockitoJUnitRunner::class)
class GetSortedWordsUseCaseTest : CoroutineTest() {

    private val word1 = Word("word1", frequency = 2)
    private val word2 = Word("word20", frequency = 1)
    private val exampleList = listOf(word1, word2)

    private val wordsRepository = Mockito.mock(WordsRepository::class.java).stub {
        onBlocking { getWords() }.doReturn(exampleList)
    }
    private lateinit var useCase: GetSortedWordsUseCase

    @Before
    fun setup() {
        useCase = GetSortedWordsUseCase(
            wordsRepository,
            testDispatcher
        )
    }

    @Test
    fun `verify frequency sorting`() = runWithDispatcher {
        val sortedResult = useCase.execute(SortingOption.Frequency)
        val resultList = (sortedResult as UseCaseOutput.Success).value
        assertThat(resultList[0]).isEqualTo(word2)
        assertThat(resultList[1]).isEqualTo(word1)
    }

    @Test
    fun `verify frequency descending sorting`() = runWithDispatcher {
        val sortedResult = useCase.execute(SortingOption.FrequencyDesc)
        val resultList = (sortedResult as UseCaseOutput.Success).value
        assertThat(resultList[0]).isEqualTo(word1)
        assertThat(resultList[1]).isEqualTo(word2)
    }

    @Test
    fun `verify alphabetical sorting`() = runWithDispatcher {
        val resultList = sortBy(SortingOption.Alphabetical)
        assertThat(resultList[0]).isEqualTo(word1)
        assertThat(resultList[1]).isEqualTo(word2)
    }

    @Test
    fun `verify alphabetical descending sorting`() = runWithDispatcher {
        val resultList = sortBy(SortingOption.AlphabeticalDesc)
        assertThat(resultList[0]).isEqualTo(word2)
        assertThat(resultList[1]).isEqualTo(word1)
    }

    @Test
    fun `verify length sorting`() = runWithDispatcher {
        val resultList = sortBy(SortingOption.Length)
        assertThat(resultList[0]).isEqualTo(word1)
        assertThat(resultList[1]).isEqualTo(word2)
    }

    @Test
    fun `verify length descending sorting`() = runWithDispatcher {
        val resultList = sortBy(SortingOption.LengthDesc)
        assertThat(resultList[0]).isEqualTo(word2)
        assertThat(resultList[1]).isEqualTo(word1)
    }

    private suspend fun sortBy(option: SortingOption): List<Word> {
        val sortedResult = useCase.execute(option)
        return (sortedResult as UseCaseOutput.Success).value
    }
}