package com.jean.reactivecoroutinestudy.notification

import com.jean.reactivecoroutinestudy.notification.event.Event
import com.jean.reactivecoroutinestudy.notification.service.NotificationService
import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.concurrent.atomic.AtomicInteger

private val log = KotlinLogging.logger {}

@RequestMapping("/api/notifications")
@RestController
class NotificationApi(
    private val notificationService: NotificationService
) {

    @GetMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getNotifications(): Flux<ServerSentEvent<String>> {
        return notificationService.getMessageFromSink()
            .map {
                val id = lastEventId.getAndIncrement().toString()
                ServerSentEvent
                    .builder(it)
                    .event("notification")
                    .id(id)
                    .comment("this is notification")
                    .build()
            }
    }

    @PostMapping
    fun addNotification(@RequestBody event: Event): Mono<String> {
        notificationService.tryEmitNext(event.toMessage)
        return Mono.just("ok")
    }

    companion object {
        val lastEventId = AtomicInteger(1)
    }
}
