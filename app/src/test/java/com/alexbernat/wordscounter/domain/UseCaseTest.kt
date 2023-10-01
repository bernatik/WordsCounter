package com.alexbernat.wordscounter.domain

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
open class UseCaseTest {

    protected val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    protected fun runWithDispatcher(testAction: suspend () -> Unit) = runTest(testDispatcher) {
        testAction()
    }
}