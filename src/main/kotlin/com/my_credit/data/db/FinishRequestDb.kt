package com.my_credit.data.db

import com.my_credit.data.model.FinishRequestModel
import org.jetbrains.exposed.sql.*
import java.util.*

object FinishRequestDb : Table("finish_request") {

    private val id = FinishRequestDb.long("id")
    private val creditId = FinishRequestDb.long("credit_id")
    private val userId = FinishRequestDb.varchar("user_id", 100)
    private val time = FinishRequestDb.long("time")

    fun insert(model: FinishRequestModel) {
        FinishRequestDb.insert {
            it[userId] = model.userId
            it[creditId] = model.creditId
            it[time] = model.time.time
        }
    }

    fun remove(id: Long) {
        FinishRequestDb.deleteWhere { FinishRequestDb.id.eq(id) }
    }

    fun getByUserId(userId: String): List<FinishRequestModel> =
        FinishRequestDb.select { FinishRequestDb.userId eq userId }.mapToModels()

    fun getByCreditId(creditId: Long): FinishRequestModel? =
        FinishRequestDb.select { FinishRequestDb.creditId eq creditId }.singleOrNull()?.mapToModel()

    fun getById(requestId: Long): FinishRequestModel? =
        FinishRequestDb.select { id eq requestId }.singleOrNull()?.mapToModel()

    fun getAllRequest(): List<FinishRequestModel> =
        FinishRequestDb.selectAll().mapToModels()

    private fun Query.mapToModels() = this.map {
        FinishRequestModel(
            it[userId],
            it[creditId],
            it[id],
            Date(it[time])
        )
    }

    private fun ResultRow.mapToModel() = this.run {
        FinishRequestModel(
            this[userId],
            this[creditId],
            this[id],
            Date(this[time])
        )
    }

}