package com.martdev.android.expensetrackr.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
                        @ColumnInfo(name = "date_in_String")
                        var dateString: String? = null,
                        @ColumnInfo(name = "description")
                        var description: String? = null)