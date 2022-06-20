package com.my_credit.util.exception

import com.my_credit.util.error.DisplayError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun ApplicationCall.respondByException(exception: Throwable, defaultStatus: HttpStatusCode? = null, defaultMessage: String? = null) {
    if (exception is DisplayError) {
        exception.respond(this)
    } else {
        (exception.message)?.let {
            respond(
                if (this is BaseHttpException) this.httpStatus() else defaultStatus ?: HttpStatusCode.BadRequest,
                defaultMessage ?: it
            )
        } ?: run {
            respond(
                if (this is BaseHttpException) this.httpStatus() else defaultStatus ?: HttpStatusCode.BadRequest
            )
        }
    }
}