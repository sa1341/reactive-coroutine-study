package com.jean.reactivecoroutinestudy.coroutine.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

suspend fun squareFlowFunc(x: Int): Int {
    delay(100)
    log.info { "current: $x" }
    return x * x
}

fun main() = runBlocking {
    val squareFlow: Flow<Int> = flow {
        emit(squareFlowFunc(10))
        emit(squareFlowFunc(20))
        emit(squareFlowFunc(30))
    }.flowOn(Dispatchers.IO)

    squareFlow.collect {
        log.info { "value: $it" }
    }
}
