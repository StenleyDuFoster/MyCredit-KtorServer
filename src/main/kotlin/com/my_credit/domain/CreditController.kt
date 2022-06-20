package com.my_credit.domain

import com.my_credit.data.db.CreditDb
import com.my_credit.data.db.FinishRequestDb
import com.my_credit.data.db.TokenDb
import com.my_credit.data.db.UserDb
import com.my_credit.data.model.CreditModel
import com.my_credit.data.model.FinishRequestModel
import com.my_credit.util.error.CreditAlreadyStartFinishProcedError
import com.my_credit.util.error.CreditNullError
import com.my_credit.util.exception.ForbidenException
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object CreditController {

    fun getCredits(token: String): ArrayList<CreditModel> = transaction {
        val user = UserDb.findUser(TokenDb.getUserId(token) ?: throw ForbidenException())
        if (user.isAdmin) {
            CreditDb.getAll()
        } else {
            CreditDb.getUserCredit(user.userId)
        }
    }

    fun saveCredit(token: String, credit: CreditModel) = transaction {
        if (TokenDb.getUserId(token) != credit.userId) {
            throw ForbidenException("The user can only add debt to himself")
        }
        CreditDb.insert(credit)
    }

    fun finishCredit(creditId: Long, token: String): Unit = transaction {
        val credit = CreditDb.getCreditById(creditId) ?: throw CreditNullError()
        if (UserDb.findUser(TokenDb.getUserId(token) ?: throw ForbidenException()).isAdmin) {
            FinishRequestDb.remove(creditId)
            CreditDb.update(credit.apply { finishTime = Date() })
        } else {
            if (FinishRequestDb.getByCreditId(creditId).isEmpty()) {
                FinishRequestDb.insert(FinishRequestModel(credit))
            } else {
                throw CreditAlreadyStartFinishProcedError()
            }
        }
    }

}