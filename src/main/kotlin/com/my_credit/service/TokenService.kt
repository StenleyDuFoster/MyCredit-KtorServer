package com.my_credit.service

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.time.Duration.Companion.days

object TokenService {

    fun start() {
        GlobalScope.launch {
            while (true) {
                removeExpireTokens()
                delay(1.days)
            }
        }
    }

    private fun removeExpireTokens() {
        kotlin.runCatching {
            transaction {

            }
        }
    }

}