package com.my_credit.domain

import com.my_credit.data.db.TokenDb
import com.my_credit.data.db.UserDb
import com.my_credit.data.model.TokenModel
import org.jetbrains.exposed.sql.transactions.transaction

object UserController {

    fun login(login: String): Result<TokenModel> =
        runCatching<TokenModel> {
            transaction {
                TokenDb.createToken(UserDb.findUser(login).userId)
            }
        }

}