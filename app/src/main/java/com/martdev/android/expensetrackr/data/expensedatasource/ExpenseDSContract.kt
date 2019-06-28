package com.martdev.android.expensetrackr.data.expensedatasource

import com.martdev.android.expensetrackr.data.DailyExpense
import com.martdev.android.expensetrackr.data.MonthlyExpense

interface ExpenseDSContract {

    interface LoadExpenses<T> {

        fun onExpensesLoaded(expenses: T)
    }

    interface LoadExpense<T> {

        fun onExpenseLoaded(expense: T)
    }

    fun getMonthlyExpenses(monthlyExpenses: LoadExpenses<List<MonthlyExpense>>)

    fun getDailyExpenses(date: String, dailyExpenses: LoadExpenses<List<DailyExpense>>)

    fun getDailyExpByCategory(date: String, category: String, expenseByCategory: LoadExpenses<List<DailyExpense>>)

    fun getMonthlyExpense(expenseId: String, monthlyExpense: LoadExpense<MonthlyExpense>)

    fun getDailyExpense(expenseId: String, dailyExpense: LoadExpense<DailyExpense>)

    fun insertMonthlyExpense(monthlyExpense: MonthlyExpense)

    fun insertDailyExpense(dailyExpense: DailyExpense)

    fun deleteMonthlyExpense(monthlyExpId: String)

    fun deleteDailyExpense(dailyExpId: String)

    fun updateDailyExpense(dailyExpense: DailyExpense)
}