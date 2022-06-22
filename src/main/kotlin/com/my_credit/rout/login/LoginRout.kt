package com.my_credit.rout.login

import com.my_credit.domain.UserController
import com.my_credit.util.constant.UrlConstant
import com.my_credit.util.error.IncorrectLoginCode
import com.my_credit.util.exception.respondByException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun Application.configureLoginRouting() {

    routing {
        post("/${UrlConstant.Login.value}") {
            kotlin.runCatching {
                Json.decodeFromString<LoginRemote>(call.receive())
            }.onFailure {
                call.respond(HttpStatusCode.BadRequest)
            }.onSuccess {
                kotlin.runCatching {
                    UserController.createTokenByUserId(it.code)
                }.onFailure {
                    call.respondByException(it)
                }.onSuccess { resultToken ->
                    call.respond(TokenRemote(resultToken))
                }.onFailure {
                    call.respondByException(it)
                }
            }
        }
    }
}
