package com.my_credit.util.exception

import io.ktor.http.*

abstract class BaseHttpException(message: String? = null): Exception(message) {

    abstract fun httpStatus(): HttpStatusCode

}