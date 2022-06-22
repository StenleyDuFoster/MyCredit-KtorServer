package com.my_credit.rout.requestFinishDept

import com.my_credit.domain.RequestFinishDeptController
import com.my_credit.util.constant.UrlConstant
import com.my_credit.util.exception.ForbidenException
import com.my_credit.util.exception.respondByException
import com.my_credit.util.list.arrayMap
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRequestFinishDeptRout() {

    routing {
        get(UrlConstant.RequestFinishDept().value) {
            kotlin.runCatching {
                call.request.headers["token"] ?: throw ForbidenException()
            }.onSuccess {
                kotlin.runCatching {
                    RequestFinishDeptController.getByToken(it)
                }.onSuccess { data ->
                    call.respond(HttpStatusCode.OK, data.arrayMap { FinishRequestRemote(it) })
                }.onFailure {
                    call.respondByException(it)
                }
            }.onFailure {
                call.respondByException(it)
            }
        }
        delete(UrlConstant.RequestFinishDept.Remove().value) {
            kotlin.runCatching {
                Pair(
                    call.request.headers["token"] ?: throw ForbidenException(),
                    call.request.queryParameters["requestId"]!!.toLong()
                )
            }.onSuccess {
                kotlin.runCatching {
                    RequestFinishDeptController.deleteRequest(it.second, it.first)
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