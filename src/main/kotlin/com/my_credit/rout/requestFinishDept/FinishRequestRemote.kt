package com.my_credit.rout.requestFinishDept

import com.my_credit.data.model.FinishRequestModel
import com.my_credit.util.DateFormatter

@kotlinx.serialization.Serializable
class FinishRequestRemote(
    val id: Long,
    val userId: String,
    val creditId: Long,
    val time: String
) {

    constructor(model: FinishRequestModel) : this(
        id = model.id!!,
        userId = model.userId,
        creditId = model.creditId,
        DateFormatter.toString(model.time)
    )

}