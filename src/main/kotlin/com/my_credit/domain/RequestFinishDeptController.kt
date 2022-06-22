package com.my_credit.domain

import com.my_credit.data.db.FinishRequestDb
import com.my_credit.data.db.UserDb
import com.my_credit.data.model.FinishRequestModel
import com.my_credit.util.error.FinishDeptRequestAlreadyRemovedError
import com.my_credit.util.error.UserCanAddOrRemoveDeptOnlyToHimSelfError
import org.jetbrains.exposed.sql.transactions.transaction

object RequestFinishDeptController {

    fun getByToken(token: String): ArrayList<FinishRequestModel> = transaction {
        val userId = UserController.getUserIdByToken(token)
        val user = UserDb.findUser(userId)
        if (user.isAdmin) {
            FinishRequestDb.getAllRequest().toCollection(ArrayList())
        } else {
            FinishRequestDb.getByUserId(userId).toCollection(ArrayList())
        }
    }

    fun deleteRequest(requestId: Long, token: String): Unit = transaction {
        val userId = UserController.getUserIdByToken(token)
        val user = UserDb.findUser(userId)
        if (user.isAdmin) {
            FinishRequestDb.remove(requestId)
        } else if (FinishRequestDb.getById(requestId).get().userId == userId) {
            FinishRequestDb.remove(requestId)
        } else {
            throw UserCanAddOrRemoveDeptOnlyToHimSelfError()
        }
    }

    private fun FinishRequestModel?.get() = this ?: throw FinishDeptRequestAlreadyRemovedError()

}