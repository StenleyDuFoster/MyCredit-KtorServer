package com.my_credit.util.error

import io.ktor.http.*

enum class ErrorCode(val code: Int, val httpCode: HttpStatusCode, val message: String) {
    InternalError(0, HttpStatusCode.InternalServerError, "Internal Server Error"),
    IncorrectLoginCode(1, HttpStatusCode.Unauthorized, "Incorrect user code"),
    CreditAlreadyStartFinishProced(2, HttpStatusCode.Conflict, "The user has already started the cancellation process"),
    CreditNull(3, HttpStatusCode.NotFound, "Debt not found")
}