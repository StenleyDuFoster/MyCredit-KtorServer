package com.my_credit.domain

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.my_credit.data.db.UserDb
import com.my_credit.data.model.TokenModel
import com.my_credit.util.constant.TokenPass
import com.my_credit.util.error.IncorrectLoginCode
import com.my_credit.util.exception.ForbidenException
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object UserController {

    fun createTokenByUserId(userId: String): TokenModel {
        kotlin.runCatching {
            transaction { UserDb.findUser(userId) }
        }.onFailure {
            throw IncorrectLoginCode()
        }
        val accessExpireTime = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1)
        }.time
        return TokenModel(
            JWT.create()
                .withAudience(TokenPass.audience)
                .withIssuer(TokenPass.issuer)
                .withClaim(TokenPass.constUserId, userId)
                .withClaim(TokenPass.signature, TokenPass.accessTokenSignature)
                .withExpiresAt(accessExpireTime)
                .sign(Algorithm.HMAC256(TokenPass.secret)),
            JWT.create()
                .withAudience(TokenPass.audience)
                .withIssuer(TokenPass.issuer)
                .withClaim(TokenPass.constUserId, userId)
                .withClaim(TokenPass.signature, TokenPass.refreshTokenSignature)
                .withExpiresAt(Calendar.getInstance().apply { add(Calendar.MONTH, 1) }.time)
                .sign(Algorithm.HMAC256(TokenPass.secret)),
            userId,
            accessExpireTime
        )
    }

    fun getUserIdByToken(token: String): String {
        JWT.decode(token).also { decodedToken ->
            if (decodedToken.expiresAt.time <= Date().time) {
                throw ForbidenException()
            } else if (decodedToken.getClaim(TokenPass.signature).asString() != TokenPass.accessTokenSignature) {
                throw ForbidenException()
            }
            return decodedToken.getClaim(TokenPass.constUserId).asString()
        }
    }

}