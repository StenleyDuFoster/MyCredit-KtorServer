package com.my_credit.util.error

import io.ktor.http.*

enum class ErrorCode(val code: Int, val httpCode: HttpStatusCode, val message: String) {
    InternalError(0, HttpStatusCode.InternalServerError, "Internal Server Error"),
    IncorrectLoginCode(1, HttpStatusCode.Unauthorized, "Incorrect user code"),
    CreditAlreadyStartFinishProced(2, HttpStatusCode.Conflict, "The user has already started the cancellation process"),
    CreditNull(3, HttpStatusCode.NotFound, "Debt not found"),
    UserCanAddDeptOnlyToHimSelf(4, HttpStatusCode.BadRequest, "The user can only add or remove debt to himself"),
    CreditAlreadyFinished(5, HttpStatusCode.Conflict, "The dept already finished"),
    RequestFinishDeptNull(6, HttpStatusCode.NotFound, "The finish dept request are already removed")
}