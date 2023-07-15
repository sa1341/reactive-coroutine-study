package com.jean.reactivecoroutinestudy.book.service

import com.jean.reactivecoroutinestudy.book.domain.Book
import com.jean.reactivecoroutinestudy.book.domain.BookRepository
import com.jean.reactivecoroutinestudy.global.error.BookAlreadyExistException
import com.jean.reactivecoroutinestudy.global.error.BookNotFoundException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

private val log = KotlinLogging.logger {}

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun saveBook(book: Book): Mono<Book> {
        return verifyExistIsbn(book.isbn)
            .then(bookRepository.save(book))
    }

    private fun verifyExistIsbn(isbn: String): Mono<Void> {
        return bookRepository.findByIsbn(isbn)
            .flatMap {
                it?.let {
                    Mono.error(BookAlreadyExistException())
                } ?: Mono.empty()
            }
    }

    fun updateBook(book: Book): Mono<Book> {
        return findVerifiedBook(book.bookId)
            .map {
                it.copy(
                    titleKorean = book.titleKorean,
                    titleEnglish = book.titleEnglish,
                    description = book.description,
                    author = book.author
                )
            }.flatMap {
                bookRepository.save(it)
            }
    }

    fun findBook(bookId: Long) = findVerifiedBook(bookId)

    fun findBooks() = bookRepository.findAll().collectList()

    private fun findVerifiedBook(bookId: Long): Mono<Book> {
        return bookRepository.findById(bookId)
            .switchIfEmpty(
                Mono.error(BookNotFoundException())
            )
    }
}
