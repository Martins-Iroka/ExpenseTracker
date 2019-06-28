package com.martdev.android.expensetrackr.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "monthly_exp_table")
data class MonthlyExpense(@PrimaryKey
                          @ColumnInfo(name = "month_id")
                          var id: String = UUID.randomUUID().toString(),
                          @ColumnInfo(name = "month")
                          var date: Date?)