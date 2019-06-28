package com.martdev.android.expensetrackr.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.martdev.android.expensetrackr.data.dao.DailyExpenseDao
import com.martdev.android.expensetrackr.data.dao.IncomeDao
import com.martdev.android.expensetrackr.data.DailyExpense
import com.martdev.android.expensetrackr.data.Income
import com.martdev.android.expensetrackr.data.MonthlyExpense
import com.martdev.android.expensetrackr.data.dao.MonthlyExpDao
import com.martdev.android.expensetrackr.data.dateconverter.DateConverter

@Database(entities = [DailyExpense::class, Income::class, MonthlyExpense::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class ExpenseTrackerDB : RoomDatabase() {

    abstract fun expenseDao(): DailyExpenseDao
    abstract fun incomeDao(): IncomeDao
    abstract fun monthlyExpDao(): MonthlyExpDao

    companion object {

        private var INSTANCE: ExpenseTrackerDB? = null

        private val lock = Any()

        fun getInstance(context: Context): ExpenseTrackerDB {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ExpenseTrackerDB::class.java, "ExpenseTrack.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}