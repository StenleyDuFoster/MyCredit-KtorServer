package com.my_credit.util.error

class IncorrectLoginCode: DisplayError() {

    override val errorCode: ErrorCode = ErrorCode.IncorrectLoginCode

}