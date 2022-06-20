package com.my_credit.util.error

class InternalError: DisplayError() {

    override val errorCode: ErrorCode = ErrorCode.InternalError

}