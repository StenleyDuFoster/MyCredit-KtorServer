package com.my_credit.rout.login

import com.my_credit.data.model.TokenModel
import com.my_credit.util.DateFormatter
import java.util.*

@kotlinx.serialization.Serializable
class TokenRemote private constructor(val token: String, val refreshToken: String, val expire: String) {

    constructor(token: TokenModel) : this(
        token = token.accessToken,
        refreshToken = token.refreshToken,
        expire = DateFormatter.toString(token.expire)
    )

}