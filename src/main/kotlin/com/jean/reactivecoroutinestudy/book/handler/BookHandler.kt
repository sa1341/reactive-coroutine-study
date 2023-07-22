package com.jean.reactivecoroutinestudy.book.handler

import com.jean.reactivecoroutinestudy.book.dto.Post
import com.jean.reactivecoroutinestudy.book.service.BookService
import com.jean.reactivecoroutinestudy.global.error.BookNotFoundException
import com.jean.reactivecoroutinestudy.global.error.BusinessException
import com.jean.reactivecoroutinestudy.global.error.ErrorCode
import com.jean.reactivecoroutinestudy.global.error.ErrorResponse
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.URI
import java.util.concurrent.TimeoutException

private val log = KotlinLogging.logger {}

@Component
class BookHandler(
    private val bookService: BookService
) {
    fun createBook(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Post::class.java)
            .flatMap {
                bookService.saveBook(it.toBook())
            }.flatMap {
                ServerResponse
                    .created(URI.create("/v5/books/${it.bookId}"))
                    .build()
            }
    }

    fun getBook(request: ServerRequest): Mono<ServerResponse> {
        log.info { request.headers().toString() }
        log.info { "bookId: ${request.pathVariable("book-id")}" }
        val bookId = request.pathVariable("book-id").toLong()

        if (bookId == 2L) {
            return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .bodyValue(
                    ErrorResponse(
                        status = ErrorCode.NOT_FOUND_BOOK.status,
                        message = ErrorCode.NOT_FOUND_BOOK.message
                    )
                )
        }

        return bookService.findBook(bookId)
            .flatMap {
                ServerResponse
                    .ok()
                    .bodyValue(it)
            }.onErrorResume {
                val businessException = it as BusinessException
                ServerResponse
                    .status(businessException.errorCode.status)
                    .bodyValue(
                        ErrorResponse(
                            status = businessException.errorCode.status,
                            message = businessException.message
                        )
                    )
            }
    }
}
