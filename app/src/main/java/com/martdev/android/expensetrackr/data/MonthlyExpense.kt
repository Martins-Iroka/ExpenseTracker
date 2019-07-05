package com.martdev.android.expensetrackr.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "monthly_exp_table")
data class MonthlyExpense(@PrimaryKey
                          @ColumnInfo(name = "month_id")
                          var id: String = UUID.randomUUID().toString(),
                          @ColumnInfo(name = "month")
                          var date: Date? = null)