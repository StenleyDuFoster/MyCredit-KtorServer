package com.my_credit.util.error

import com.my_credit.util.exception.BaseHttpException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

abstract class DisplayError: BaseHttpException() {

    abstract val errorCode: ErrorCode

    override fun httpStatus(): HttpStatusCode = errorCode.httpCode

    suspend fun respond(call: ApplicationCall) =
        call.respond(errorCode.httpCode, DisplayRespond(errorCode = errorCode.code, message = errorCode.message))

}

@kotlinx.serialization.Serializable
private class DisplayRespond(
    val errorCode: Int,
    val message: String
)