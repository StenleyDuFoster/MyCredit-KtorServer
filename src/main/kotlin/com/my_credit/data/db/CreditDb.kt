package com.my_credit.data.db

import com.my_credit.data.model.CreditModel
import org.jetbrains.exposed.sql.*
import java.util.*

//ALTER TABLE your_table ADD COLUMN key_column BIGSERIAL PRIMARY KEY;
object CreditDb : Table("credit") {

    private val id = CreditDb.long("id").autoIncrement()
    private val userId = CreditDb.varchar("user_id", 50)
    private val cost = CreditDb.float("cost")
    private val description = CreditDb.varchar("description", 100)
    private val createTime = CreditDb.varchar("create_time", 100)
    private val finishTime = CreditDb.varchar("finish_time", 100)
    private val deptPayTime = CreditDb.varchar("dept_pay_time", 100)
    private val tag = CreditDb.varchar("tag", 50)

    fun insert(model: CreditModel): Long =
        CreditDb.insert {
            it[userId] = model.userId
            it[cost] = model.cost
            model.description?.let { description ->
                it[CreditDb.description] = description
            }
            it[createTime] = model.createTime?.time?.toString() ?: Date().time.toString()
            model.tag?.let { tag -> it[CreditDb.tag] = tag }
            model.finishTime?.let { finishTime -> it[CreditDb.finishTime] = finishTime.time.toString() }
            model.deptPayTime?.let { deptPayTime -> it[CreditDb.deptPayTime] = deptPayTime.time.toString() }
        } get id

    fun update(model: CreditModel) {
        CreditDb.update(where = { id.eq(model.id!!) }, body = {
            it[userId] = model.userId
            it[cost] = model.cost
            model.description?.let { description ->
                it[CreditDb.description] = description
            }
            model.createTime?.time?.toString()?.let { createTime ->
                it[CreditDb.createTime] = createTime
            }
            model.tag?.let { tag -> it[CreditDb.tag] = tag }
            model.finishTime?.let { finishTime -> it[CreditDb.finishTime] = finishTime.time.toString() }
            model.deptPayTime?.let { deptPayTime -> it[CreditDb.deptPayTime] = deptPayTime.time.toString() }
        })
    }

    fun getUserCredit(userId: String): ArrayList<CreditModel> =
        CreditDb.select { CreditDb.userId eq userId }.mapToModels()

    fun getCreditById(creditId: Long): CreditModel? = CreditDb.select { id eq creditId }.singleOrNull()?.run {
        CreditModel(
            this[id].toLong(),
            this[userId],
            this[cost],
            this[description],
            this[createTime].toLongOrNull()?.let { it1 -> Date(it1) },
            this.getOrNull(finishTime)?.toLongOrNull()?.let { it1 -> Date(it1) },
            this.getOrNull(deptPayTime)?.toLongOrNull()?.let { it1 -> Date(it1) },
            this[tag]
        )
    }

    fun getAll(): ArrayList<CreditModel> =
        CreditDb.selectAll().mapToModels()

    fun getNeedToNotyCredits() = CreditDb.select { deptPayTime.isNotNull() and deptPayTime.greater(Date().time ) }.mapToModels()

    private fun Query.mapToModels() = this.map {
        CreditModel(
            it[id],
            it[userId],
            it[cost],
            it[description],
            it[createTime]?.toLongOrNull()?.let { it1 -> Date(it1) },
            it[finishTime]?.toLongOrNull()?.let { it1 -> Date(it1) },
            it[deptPayTime]?.toLongOrNull()?.let { it1 -> Date(it1) },
            it[tag]
        )
    }.toCollection(ArrayList())

}