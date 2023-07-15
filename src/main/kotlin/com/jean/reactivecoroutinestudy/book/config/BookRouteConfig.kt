package com.jean.reactivecoroutinestudy.book.config

import com.jean.reactivecoroutinestudy.book.handler.BookHandler
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

private val log = KotlinLogging.logger {}

@Configuration
class BookRouteConfig(
    private val bookHandler: BookHandler
) {

    @Bean
    fun bookRouter(): RouterFunction<ServerResponse> = router {
        path("/api/v1/books").nest {
            accept(MediaType.APPLICATION_JSON).nest {
                POST(bookHandler::createBook)
            }
            accept(MediaType.TEXT_PLAIN).nest {
                GET("/{book-id}", bookHandler::getBook)
            }
        }
    }
}
