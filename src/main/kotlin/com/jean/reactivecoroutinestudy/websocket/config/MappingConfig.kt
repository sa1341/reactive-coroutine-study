package com.jean.reactivecoroutinestudy.websocket.config

import com.jean.reactivecoroutinestudy.websocket.handler.ChatWebSocketHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
@Configuration
class MappingConfig {

    @Bean
    fun simpleUrlHandlerMapping(chatWebSocketHandler: ChatWebSocketHandler) =
        SimpleUrlHandlerMapping().apply {
            order = 1
            urlMap = mutableMapOf<String, WebSocketHandler>("/chat" to chatWebSocketHandler)
        }
}
