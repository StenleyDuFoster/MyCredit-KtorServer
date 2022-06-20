package com.my_credit.service

import com.my_credit.data.model.CreditModel
import com.my_credit.domain.NotificationController
import com.my_credit.util.list.arrayMap
import kotlinx.coroutines.*
import java.util.*
import kotlin.time.Duration.Companion.hours

object NotificationService {

    private var startDate = Date()

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                startDate = Date()
                getTimeToFinishDepts().getWhenTimeToNotyLessThen6Hours().forEach { it.sendDelayedNotification() }
                delay(6.hours)
            }
        }
    }

    private fun getTimeToFinishDepts(): ArrayList<Pair<Date, CreditModel>> = kotlin.runCatching {
        NotificationController.getAllNotFinishedCredit().toPairWithDate()
    }.getOrNull() ?: arrayListOf()

    private fun ArrayList<CreditModel>.toPairWithDate(): ArrayList<Pair<Date, CreditModel>> =
        this.arrayMap { (it.deptPayTime ?: Date()) to it }

    private fun ArrayList<Pair<Date, CreditModel>>.getWhenTimeToNotyLessThen6Hours(): ArrayList<Pair<Date, CreditModel>> =
        this.apply {
            val removeList = arrayListOf<Pair<Date, CreditModel>>()
            forEach {
                if (it.first.time > startDate.time) {
                    removeList.add(it)
                }
            }
            this.removeAll(removeList.toSet())
        }

    private suspend fun Pair<Date, CreditModel>.sendDelayedNotification() {
        delay(this.first.time - startDate.time)
        NotificationController.sendDeptsNotification(this.second)
    }

}