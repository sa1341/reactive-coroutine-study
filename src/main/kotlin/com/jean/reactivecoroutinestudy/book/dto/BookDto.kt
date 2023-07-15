package com.jean.reactivecoroutinestudy.book.dto

import com.jean.reactivecoroutinestudy.book.domain.Book
import java.time.LocalDateTime

data class Post(
    val id: Long,
    val description: String,
    val titleKorean: String,
    val titleEnglish: String,
    val author: String,
    val isbn: String,
    val publishDate: String
) {
    fun toBook(): Book {
        return Book(
            bookId = id,
            titleKorean = titleKorean,
            titleEnglish = titleEnglish,
            description = description,
            author = author,
            isbn = isbn,
            publishDate = "2023-07-07",
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )
    }
}
