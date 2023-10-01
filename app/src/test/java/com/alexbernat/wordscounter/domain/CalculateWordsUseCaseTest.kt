package com.alexbernat.wordscounter.domain

import com.alexbernat.wordscounter.domain.model.UseCaseOutput
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
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
class CalculateWordsUseCaseTest {

    companion object {
        private const val TEST_STRING = "Test string"
    }

    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

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
    fun `verify success execution with valid input string`() = runTest(testDispatcher) {
        `when`(textRepository.loadText()).thenReturn(TEST_STRING)
        val result = useCase.execute()
        assertThat(result).isInstanceOf(UseCaseOutput.Success::class.java)
    }

    @Test
    fun `verify error execution if text loading throws exception`() = runTest(testDispatcher) {
        doThrow(RuntimeException()).`when`(textRepository).loadText()
        val result = useCase.execute()
        assertThat(result).isInstanceOf(UseCaseOutput.Error::class.java)
    }

    @Test
    fun `verify execution result is saved into repository`() = runTest(testDispatcher) {
        `when`(textRepository.loadText()).thenReturn(TEST_STRING)
        useCase.execute()
        verifyBlocking(wordsRepository) {
            save(anyList())
        }
    }
}