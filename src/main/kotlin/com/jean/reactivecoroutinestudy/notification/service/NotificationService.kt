package com.jean.reactivecoroutinestudy.notification.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Sinks

private val log = KotlinLogging.logger {}

@Service
class NotificationService {

    private val sink: Sinks.Many<String> = Sinks.many().unicast().onBackpressureBuffer()

    fun getMessageFromSink() = sink.asFlux()

    fun tryEmitNext(message: String) {
        log.info { "message: $message" }
        sink.tryEmitNext(message)
    }
}
