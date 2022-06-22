package com.my_credit.data.model

import com.my_credit.rout.credit.CreditReceive
import com.my_credit.util.DateFormatter
import java.util.*

data class CreditModel(
    var id: Long?,
    val userId: String,
    val cost: Float,
    val description: String?,
    val createTime: Date?,
    var finishTime: Date?,
    val deptPayTime: Date?,
    val tag: String?
) {

    constructor(remote: CreditReceive) : this(
        null,
        remote.userId,
        remote.cost,
        remote.description,
        DateFormatter.fromString(remote.createTime) ?: Date(),
        null,
        DateFormatter.fromString(remote.deptPayTime),
        remote.tag
    )

}