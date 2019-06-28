package com.martdev.android.expensetrackr.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
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