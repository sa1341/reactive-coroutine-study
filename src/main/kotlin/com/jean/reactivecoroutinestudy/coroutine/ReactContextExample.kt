package com.jean.reactivecoroutinestudy.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.ReactorContext
import kotlinx.coroutines.reactor.mono
import mu.KotlinLogging
import reactor.core.publisher.Mono
import reactor.util.context.Context
import java.util.concurrent.TimeUnit

private val log = KotlinLogging.logger {}

val greeting = mono {
    launch(Dispatchers.IO) {
        val context = this.coroutineContext[ReactorContext]
            ?.context
        val who = context?.get<String>("who")
        log.info { "hello, $who" }

        val newContext = (context ?: Context.empty()).put("who", "taewoo")
        launch(ReactorContext(newContext)) {
            val context = this.coroutineContext[ReactorContext]
                ?.context

            Mono.create<String> {
                val who = it.contextView().getOrDefault("who", "world")
                it.success("hello, $who")
            }.contextWrite((context ?: Context.empty()))
                .subscribe {
                    log.info(it)
                }
        }
    }
}

fun main() {
    greeting
        .contextWrite { it.put("who", "grizz") }
        .subscribe()

    TimeUnit.SECONDS.sleep(10)
}
