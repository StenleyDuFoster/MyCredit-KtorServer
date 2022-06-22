package com.my_credit.util.error

class CreditAlreadyFinishedError : DisplayError() {
    override val errorCode: ErrorCode
        get() = ErrorCode.CreditAlreadyFinished
}