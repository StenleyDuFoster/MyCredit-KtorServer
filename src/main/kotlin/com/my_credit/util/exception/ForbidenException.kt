package com.my_credit.util.exception

import io.ktor.http.*

class ForbidenException(message: String? = null) : BaseHttpException(message) {

    override fun httpStatus() = HttpStatusCode.Forbidden

}