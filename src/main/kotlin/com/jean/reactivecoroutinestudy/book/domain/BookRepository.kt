package com.jean.reactivecoroutinestudy.book.domain

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface BookRepository : ReactiveCrudRepository<Book, Long> {
    fun findByIsbn(isbn: String): Mono<Book>
}
