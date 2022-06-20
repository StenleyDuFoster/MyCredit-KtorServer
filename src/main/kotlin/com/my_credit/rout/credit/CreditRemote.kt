package com.my_credit.rout.credit

import com.my_credit.data.model.CreditModel
import com.my_credit.util.DateFormatter

@kotlinx.serialization.Serializable
data class CreditRemote(
    val userId: String,
    val cost: Float,
    val description: String? = null,
    val createTime: String? = null,
    val finishTime: String? = null,
    val deptPayTime: String? = null,
    val tag: String? = null
) {

    constructor(model: CreditModel) : this(
        model.userId,
        model.cost,
        model.description,
        DateFormatter.toString(model.createTime),
        DateFormatter.toString(model.finishTime),
        DateFormatter.toString(model.deptPayTime),
        model.tag
    )

}
