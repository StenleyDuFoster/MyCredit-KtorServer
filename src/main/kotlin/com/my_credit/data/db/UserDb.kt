package com.my_credit.data.db

import com.my_credit.data.model.UserModel
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select

object UserDb : Table("user") {

    private val userName = UserDb.varchar("user_name", 50)
    private val userId = UserDb.varchar("user_id", 50)
    private val userPhoto = UserDb.varchar("user_photo", 200)
    private val isAdmin = UserDb.bool("is_admin")

    fun findUser(userId: String) =
        UserDb.select {
            UserDb.userId eq userId
        }.single().run {
            UserModel(
                userId = this[UserDb.userId],
                userName = this[userName],
                userPhoto = this[userPhoto],
                isAdmin = this[isAdmin]
            )
        }

}