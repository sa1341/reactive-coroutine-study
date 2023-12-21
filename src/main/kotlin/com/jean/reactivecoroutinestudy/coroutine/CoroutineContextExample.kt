package com.jean.reactivecoroutinestudy.coroutine

import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import kotlin.coroutines.suspendCoroutine

private val log = KotlinLogging.logger {}

private suspend fun child() {
    val result = suspendCoroutine<Int> { cont ->
        log.info { "context by continuation: ${cont.context}" }
    }

    log.info { "result: $result" }
}

fun main() {
    runBlocking {
        log.info { "context in CoroutineScope: ${this.coroutineContext}" }
        child()
    }
}
