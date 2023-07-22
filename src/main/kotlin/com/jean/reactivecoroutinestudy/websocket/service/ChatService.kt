package com.jean.reactivecoroutinestudy.websocket.service

import com.jean.reactivecoroutinestudy.websocket.handler.Chat
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.util.concurrent.ConcurrentHashMap

@Service
class ChatService {
    companion object {
        val chatSinkMap: ConcurrentHashMap<String, Sinks.Many<Chat>> = ConcurrentHashMap()
    }

    fun register(iam: String): Flux<Chat> {
        val sink = Sinks.many().unicast().onBackpressureBuffer<Chat>()
        chatSinkMap[iam] = sink
        return sink.asFlux()
    }

    fun sendChat(iam: String, chat: Chat): Boolean {
        val sink = chatSinkMap[iam]
        sink?.let {
            it.tryEmitNext(chat)
            return true
        } ?: return false
    }
}
