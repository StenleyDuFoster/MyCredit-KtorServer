package com.my_credit.data.db

import com.my_credit.data.model.TokenModel
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.*

object TokenDb : Table("token") {

    private val id = TokenDb.varchar("id", 50)
    private val userId = TokenDb.varchar("user_id", 50)
    private val accessToken = TokenDb.varchar("access_token", 200)
    private val refreshToken = TokenDb.varchar("refresh_token", 100)
    private val expireTime = TokenDb.long("expire_time")

    fun createToken(userId: String): TokenModel {
        val model = TokenModel(
            userId = userId,
            accessToken = UUID.randomUUID().toString(),
            refreshToken = UUID.randomUUID().toString(),
            expire = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, 1)
            }.time
        )
        TokenDb.insert {
            it[this.userId] = model.userId
            it[accessToken] = model.accessToken
            it[refreshToken] = model.refreshToken
            it[expireTime] = model.expire.time
        }
        return model
    }

    fun getUserId(accessToken: String): String? =
        TokenDb.select { TokenDb.accessToken eq accessToken }.singleOrNull()?.get(userId)

    fun removeAllExpiredToken() {
        TokenDb.deleteWhere {
            expireTime less Calendar.getInstance().apply {
                add(Calendar.MONTH, 6)
            }.timeInMillis
        }
    }

}