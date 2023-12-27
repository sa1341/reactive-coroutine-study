package com.jean.reactivecoroutinestudy.coroutine

import kotlinx.coroutines.*
import mu.KotlinLogging
import kotlin.coroutines.EmptyCoroutineContext
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

        val cs = CoroutineScope(EmptyCoroutineContext)

        log.info { "context = ${cs.coroutineContext}" }
        log.info { "class name = ${cs.javaClass.simpleName}" }

        val job = cs.launch {
            delay(100)
            log.info { "context: ${this.coroutineContext}" }
            log.info { "class name = ${this.javaClass.simpleName}" }
        }

        log.info { "step1" }
        job.join()
        log.info { "step2" }

        // child()
    }
}
