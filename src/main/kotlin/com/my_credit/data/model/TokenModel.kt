package com.my_credit.data.model

import java.util.Date

class TokenModel(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
    val id: String? = null,
    val expire: Date
)