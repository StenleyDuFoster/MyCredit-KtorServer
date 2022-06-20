package com.my_credit.domain

import com.my_credit.data.db.CreditDb
import com.my_credit.data.model.CreditModel
import org.jetbrains.exposed.sql.transactions.transaction

object NotificationController {

    fun getAllNotFinishedCredit(): ArrayList<CreditModel> = transaction {
        CreditDb.getNeedToNotyCredits()
    }

    fun sendDeptsNotification(credit: CreditModel) {

    }

}