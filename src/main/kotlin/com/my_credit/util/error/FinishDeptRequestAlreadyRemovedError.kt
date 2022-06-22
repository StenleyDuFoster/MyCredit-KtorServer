package com.my_credit.util.error

class FinishDeptRequestAlreadyRemovedError : DisplayError() {
    override val errorCode: ErrorCode
        get() = ErrorCode.RequestFinishDeptNull
}