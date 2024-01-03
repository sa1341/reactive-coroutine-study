package com.jean.reactivecoroutinestudy.coroutine.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import mu.KotlinLogging
import reactor.core.publisher.Flux
import kotlin.coroutines.EmptyCoroutineContext

private val log = KotlinLogging.logger {}

suspend fun main() {
    val cs = CoroutineScope(EmptyCoroutineContext)
    val job = cs.launch {
        val numbers = flowOf(1, 2, 3)
        numbers.collect {
            log.info { "value1: $it" }
        }
        log.info { "value1 End" }

        val numbers2 = listOf(1, 2, 3).asFlow()
        numbers2.collect {
            log.info { "value2: $it" }
        }
        log.info { "value2 End" }

        val numbers3 = (1..3).asFlow()
        numbers3.collect {
            log.info { "value3: $it" }
        }
        log.info { "value3 End" }

        val numbers4 = Flux.just(1, 2, 3).asFlow()
        numbers4.collect {
            log.info { "value4: $it" }
        }
        log.info { "value4 End" }
    }

    job.join()
}
