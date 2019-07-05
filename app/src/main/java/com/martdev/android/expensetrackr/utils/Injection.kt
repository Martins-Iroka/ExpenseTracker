package com.martdev.android.expensetrackr.utils

import android.content.Context
import com.martdev.android.expensetrackr.data.database.ExpenseTrackerDB
import com.martdev.android.expensetrackr.data.expensedatasource.ExpenseLocalDataSource
import com.martdev.android.expensetrackr.data.expensedatasource.ExpenseRepo

object Injection {

    fun provideExpenseRepo(context: Context): ExpenseRepo {
        val database = ExpenseTrackerDB.getInstance(context)
        val dataSource = ExpenseLocalDataSource.getInstance(AppExecutors(), database.monthlyExpDao(),
                database.expenseDao())
        return ExpenseRepo.getInstance(dataSource as ExpenseLocalDataSource)
    }
}