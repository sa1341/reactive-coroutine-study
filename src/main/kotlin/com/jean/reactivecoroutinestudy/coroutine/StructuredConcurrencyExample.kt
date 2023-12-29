package com.jean.reactivecoroutinestudy.coroutine

import kotlinx.coroutines.*
import mu.KotlinLogging
import kotlin.coroutines.EmptyCoroutineContext

private val log = KotlinLogging.logger {}

fun main() = runBlocking {
    val job = CoroutineScope(EmptyCoroutineContext).launch {
        log.info { "context in root = $coroutineContext" }
        log.info { "step1" }
        val result = coroutineScope {
            log.info { "context in root = $coroutineContext" }
            val deferred1 = async {
                delay(100)
                100
            }

            val deferred2 = async {
                delay(100)
                200
            }

            val deferred3 = async {
                delay(100)
                300
            }

            deferred1.await() + deferred2.await() + deferred3.await()
        }

        log.info { "result = $result" }
        log.info { "step2" }
    }

    job.join()
}
