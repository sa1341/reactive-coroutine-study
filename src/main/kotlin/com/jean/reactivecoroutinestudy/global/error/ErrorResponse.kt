package com.jean.reactivecoroutinestudy.global.error

import org.springframework.http.HttpStatus

data class ErrorResponse(
    val status: HttpStatus,
    val message: String?
)
