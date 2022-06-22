package com.my_credit.util.error

class UserCanAddOrRemoveDeptOnlyToHimSelfError: DisplayError() {

    override val errorCode: ErrorCode = ErrorCode.UserCanAddDeptOnlyToHimSelf

}