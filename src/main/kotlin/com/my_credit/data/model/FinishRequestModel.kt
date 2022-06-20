package com.my_credit.data.model

import java.util.Date

class FinishRequestModel(
    val userId: String,
    val creditId: Long,
    val id: Long? = null,
    val time: Date
) {

    constructor(credit: CreditModel) : this(userId = credit.userId, creditId = credit.id!!, null, Date())

}