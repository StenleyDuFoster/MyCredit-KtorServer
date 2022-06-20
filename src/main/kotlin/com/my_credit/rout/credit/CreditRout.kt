package com.my_credit.rout.credit

import com.my_credit.data.model.CreditModel
import com.my_credit.domain.CreditController
import com.my_credit.util.exception.ForbidenException
import com.my_credit.util.exception.respondByException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun Application.configureCreditRouting() {

    routing {
        get(UrlConstant.Credit.List().value) {
            kotlin.runCatching {
                call.request.headers["token"] ?: throw ForbidenException()
            }.onSuccess {
                kotlin.runCatching {
                    CreditController.getCredits(it)
                }.onSuccess { data ->
                    call.respond(data.map {
                        CreditRemote(it)
                    })
                }.onFailure {
                    call.respondByException(it)
                }
            }.onFailure {
                call.respondByException(it)
            }
        }
        post(UrlConstant.Credit.Add().value) {
            kotlin.runCatching {
                Pair(
                    call.request.headers["token"] ?: throw ForbidenException(),
                    Json.decodeFromString<CreditReceive>(call.receive())
                )
            }.onSuccess {
                kotlin.runCatching {
                    CreditController.saveCredit(it.first, CreditModel(it.second))
                }.onSuccess {
                    call.respond(HttpStatusCode.OK)
                }.onFailure {
                    call.respondByException(it)
                }
            }.onFailure {
                call.respondByException(it)
            }
        }
        post(UrlConstant.Credit.Finish().value) {
            kotlin.runCatching {
                Pair(
                    call.request.queryParameters.get("creditId")!!.toLong(),
                    call.request.headers["token"] ?: throw ForbidenException()
                )
            }.onSuccess {
                kotlin.runCatching {
                    CreditController.finishCredit(it.first, it.second)
                }.onSuccess {
                    call.respond(HttpStatusCode.OK)
                }.onFailure {
                    call.respondByException(it)
                }
            }.onFailure {
                call.respondByException(it)
            }
        }
    }

}