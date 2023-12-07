package com.mvvm.demo.presentation.viewmodel

import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class CoroutineTestRule : TestWatcher() {

    val testCoroutineScope = TestCoroutineScope()

    override fun starting(description: Description?) {
        super.starting(description)
        testCoroutineScope.runBlockingTest {
            // Set up any initial conditions if needed
        }
    }

    override fun finished(description: Description?) {
        super.finished(description)
        testCoroutineScope.cleanupTestCoroutines()
    }
}