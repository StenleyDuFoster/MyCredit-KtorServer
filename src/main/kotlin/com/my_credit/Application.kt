package com.my_credit

import com.my_credit.rout.credit.configureCreditRouting
import com.my_credit.rout.login.configureLoginRouting
import com.my_credit.service.NotificationService
import com.my_credit.service.TokenService
import com.my_credit.util.constant.PgPass
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/credit_main",
        driver = "org.postgresql.Driver",
        password = PgPass.PASS,
        user = "postgres"
    )

    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureLoginRouting()
        configureCreditRouting()
        install(ContentNegotiation) {
            json(json = Json {
                encodeDefaults = false
                isLenient = true
                allowSpecialFloatingPointValues = true
                allowStructuredMapKeys = true
                prettyPrint = false
                useArrayPolymorphism = false
            })
        }
    }.start(wait = true)

    TokenService.start()
    NotificationService.start()
}
