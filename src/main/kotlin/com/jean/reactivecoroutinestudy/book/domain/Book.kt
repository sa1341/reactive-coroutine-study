package com.jean.reactivecoroutinestudy.book.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

data class Book(
    @Id
    val bookId: Long,
    val titleKorean: String,
    val titleEnglish: String,
    val description: String,
    val author: String,
    val isbn: String,
    val publishDate: String,
    @CreatedDate
    val createdAt: LocalDateTime,
    @LastModifiedDate
    @Column(value = "last_modified_at")
    val modifiedAt: LocalDateTime
)
