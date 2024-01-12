package com.jean.reactivecoroutinestudy.coroutine.channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import kotlin.coroutines.EmptyCoroutineContext

private val log = KotlinLogging.logger {}

suspend fun main() = runBlocking {
    val channel = Channel<Int>()

    val job = CoroutineScope(EmptyCoroutineContext).launch {
        launch(Dispatchers.IO) {
            log.info { "sending" }
            channel.send(1)
            channel.send(2)
            channel.send(13)
            channel.close()
        }

        for (value in channel) {
            log.info { "value = $value" }
        }
    }

    job.join()
}
