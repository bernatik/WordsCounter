package com.alexbernat.wordscounter.domain

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest

open class CoroutineTest {

    protected val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    protected fun runWithDispatcher(testAction: suspend () -> Unit) = runTest(testDispatcher) {
        testAction()
    }
}