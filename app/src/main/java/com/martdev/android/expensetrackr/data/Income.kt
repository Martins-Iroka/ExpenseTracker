package com.martdev.android.expensetrackr.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "income")
data class Income(@PrimaryKey @ColumnInfo(name = "incomeId")
                  val incomeId: String = UUID.randomUUID().toString(),
                  @ColumnInfo(name = "income")
                  var income: String,
                  @ColumnInfo(name = "balance")
                  var balance: String,
                  @ColumnInfo(name = "accountName")
                  var accountName: String,
                  @ColumnInfo(name = "accountType")
                  var accountType: String)