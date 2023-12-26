package com.jean.reactivecoroutinestudy.coroutine

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, e ->
        log.error { "exception caught" }
    }

    val job = CoroutineScope(Dispatchers.IO + handler).launch {
        launch {
            launch {
                throw IllegalStateException("exception in launch")
            }
        }
    }

    job.join()
}
