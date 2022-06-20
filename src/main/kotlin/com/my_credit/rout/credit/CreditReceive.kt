package com.my_credit.rout.credit

@kotlinx.serialization.Serializable
class CreditReceive(
    val userId: String,
    val cost: Float,
    val description: String? = null,
    val createTime: String? = null,
    val deptPayTime: String? = null,
    val tag: String? = null
)