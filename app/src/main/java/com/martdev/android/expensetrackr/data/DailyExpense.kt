package com.martdev.android.expensetrackr.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "expense")
data class DailyExpense(@PrimaryKey @ColumnInfo(name = "expenseId")
                        val id: String = UUID.randomUUID().toString(),
                        @ColumnInfo(name = "amount")
                        var amount: String? = null,
                        @ColumnInfo(name = "category")
                        var category: String? = null,
                        @ColumnInfo(name = "categoryId")
                        var categoryId: Int? = null,
                        @ColumnInfo(name = "date")
                        var date: Date = Date(),
                        @ColumnInfo(name = "description")
                        var description: String? = null)