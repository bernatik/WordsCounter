package com.alexbernat.wordscounter.domain

import com.alexbernat.wordscounter.domain.model.UseCaseOutput
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.anyList
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.verifyBlocking

@RunWith(MockitoJUnitRunner::class)
class CalculateWordsUseCaseTest : UseCaseTest() {

    companion object {
        private const val TEST_STRING = "Test string"
    }

    private val textRepository = mock(TextRepository::class.java)
    private val wordsRepository = mock(WordsRepository::class.java)

    private lateinit var useCase: CalculateWordsUseCase

    @Before
    fun setup() {
        useCase = CalculateWordsUseCase(
            textRepository,
            wordsRepository,
            testDispatcher
        )
    }

    @Test
    fun `verify success execution with valid input string`() = runWithDispatcher {
        `when`(textRepository.loadText()).thenReturn(TEST_STRING)
        val result = useCase.execute()
        assertThat(result).isInstanceOf(UseCaseOutput.Success::class.java)
    }

    @Test
    fun `verify error execution if text loading throws exception`() = runWithDispatcher {
        doThrow(RuntimeException()).`when`(textRepository).loadText()
        val result = useCase.execute()
        assertThat(result).isInstanceOf(UseCaseOutput.Error::class.java)
    }

    @Test
    fun `verify execution result is saved into repository`() = runWithDispatcher {
        `when`(textRepository.loadText()).thenReturn(TEST_STRING)
        useCase.execute()
        verifyBlocking(wordsRepository) {
            save(anyList())
        }
    }
}