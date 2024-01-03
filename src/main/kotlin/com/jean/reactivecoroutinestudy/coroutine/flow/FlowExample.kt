package com.jean.reactivecoroutinestudy.coroutine.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import mu.KotlinLogging
import java.lang.Exception

private val log = KotlinLogging.logger {}

suspend fun square(x: Int): Int {
    try {
        delay(100)
    } catch (e: Exception) {
        log.error { "Exception: ${e.message}" }
    }
    return x * x
}

fun main() = runBlocking {
    val squareFlow: Flow<Int> = flow {
        emit(square(10))
        emit(square(20))
        emit(square(30))
    }

    withTimeoutOrNull(250) {
        squareFlow.collect {
            log.info { "value: $it" }
        }
    }

    log.info { "finished" }
}
