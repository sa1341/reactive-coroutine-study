package com.jean.reactivecoroutinestudy.global.error

import org.springframework.http.HttpStatus

sealed class BusinessException(
    val errorCode: ErrorCode,
    val details: Any? = null,
    val description: String? = null,
    override val cause: Throwable? = null
) : RuntimeException(
    """
     [ErrorCode] : ${errorCode.code}
     [ErrorMessage] : ${errorCode.message}
     [Description] : $description
     [Details] : $details
    """.trimIndent(),
    cause
)

class BookNotFoundException : BusinessException(errorCode = ErrorCode.NOT_FOUND_BOOK)
class BookAlreadyExistException : BusinessException(errorCode = ErrorCode.BOOK_ALREADY_EXIST)
class BadRequestException(description: String?) : BusinessException(
    errorCode = ErrorCode.BAD_REQUEST,
    description = description
)

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
    NOT_FOUND_BOOK(HttpStatus.NOT_FOUND, "책이 존재하지 않습니다."),
    BOOK_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "책이 이미 등록되어 있습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다.");

    val code: String
        get() {
            return this.name
        }
}
