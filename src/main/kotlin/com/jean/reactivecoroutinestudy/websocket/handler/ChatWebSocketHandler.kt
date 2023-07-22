package com.jean.reactivecoroutinestudy.websocket.handler

import com.jean.reactivecoroutinestudy.websocket.service.ChatService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class ChatWebSocketHandler(
    private val chatService: ChatService
) : WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> {
        val iam = session.attributes["iam"] as String
        val chatFlux = chatService.register(iam)
        chatService.sendChat(iam, Chat(message = "${iam}님 채팅방에 오신 것을 환영합니다.", from = "system"))

        session.receive()
            .doOnNext {
                val payload = it.payloadAsText

                val split = payload.split(":")
                val to = split[0].trim()
                val message = split[1].trim()

                val result = chatService.sendChat(
                    iam = to,
                    chat = Chat(message = message, from = to)
                )

                if (!result) {
                    chatService.sendChat(iam, Chat(message = "대화 상대가 없습니다.", from = "system"))
                }
            }.subscribe()

        return session.send(
            chatFlux.map { chat ->
                session.textMessage("${chat.from}: ${chat.message}")
            }
        )
    }
}

data class Chat(
    val message: String,
    val from: String
)
