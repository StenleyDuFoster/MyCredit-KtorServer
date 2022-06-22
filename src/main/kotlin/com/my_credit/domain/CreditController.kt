package com.my_credit.domain

import com.my_credit.data.db.CreditDb
import com.my_credit.data.db.FinishRequestDb
import com.my_credit.data.db.UserDb
import com.my_credit.data.model.CreditModel
import com.my_credit.data.model.FinishRequestModel
import com.my_credit.util.error.CreditAlreadyStartFinishProcedError
import com.my_credit.util.error.CreditNullError
import com.my_credit.util.error.CreditAlreadyFinishedError
import com.my_credit.util.error.UserCanAddOrRemoveDeptOnlyToHimSelfError
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object CreditController {

    fun getCredits(token: String): ArrayList<CreditModel> = transaction {
        val user = UserDb.findUser(UserController.getUserIdByToken(token))
        if (user.isAdmin) {
            CreditDb.getAll()
        } else {
            CreditDb.getUserCredit(user.userId)
        }
    }

    fun saveCredit(token: String, credit: CreditModel): CreditModel = transaction {
        val userId = UserController.getUserIdByToken(token)
        if (!UserDb.findUser(userId).isAdmin && userId != credit.userId) {
            throw UserCanAddOrRemoveDeptOnlyToHimSelfError()
        }
        credit.id = CreditDb.insert(credit)
        credit
    }

    fun finishCredit(creditId: Long, token: String): Unit = transaction {
        val credit = CreditDb.getCreditById(creditId) ?: throw CreditNullError()
        if (UserDb.findUser(UserController.getUserIdByToken(token)).isAdmin) {
            if (CreditDb.getCreditById(creditId).get().finishTime != null) {
                throw CreditAlreadyFinishedError()
            }
            FinishRequestDb.remove(creditId)
            CreditDb.update(credit.apply { finishTime = Date() })
        } else {
            if (FinishRequestDb.getByCreditId(creditId) == null) {
                FinishRequestDb.insert(FinishRequestModel(credit))
            } else {
                throw CreditAlreadyStartFinishProcedError()
            }
        }
    }

    private fun CreditModel?.get() = this ?: throw CreditNullError()

}