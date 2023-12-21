package com.jean.reactivecoroutinestudy.coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.reactor.mono
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

private val log = KotlinLogging.logger {}

@RestController
@RequestMapping("/greet")
class SuspendingController {

    @GetMapping
    suspend fun greet(): Mono<String> {
        log.info { "thread = ${Thread.currentThread().name}" }
        return mono {
            delay(1000)
            "hello"
        }
    }
}
