package com.martdev.android.expensetrackr.data.expensedatasource

import com.martdev.android.expensetrackr.data.DailyExpense
import com.martdev.android.expensetrackr.data.MonthlyExpense

class ExpenseRepo private constructor (private val dataSource: ExpenseLocalDataSource) : ExpenseDSContract{

    companion object {

        private var INSTANCE: ExpenseRepo? = null

        @JvmStatic
        fun getInstance(dataSource: ExpenseLocalDataSource): ExpenseRepo {
            return INSTANCE?: ExpenseRepo(dataSource).apply {
                INSTANCE = this
            }
        }
    }

    override fun getMonthlyExpenses(monthlyExpenses: ExpenseDSContract.LoadExpenses<List<MonthlyExpense>>) {
        dataSource.getMonthlyExpenses(object : ExpenseDSContract.LoadExpenses<List<MonthlyExpense>> {
            override fun onExpensesLoaded(expenses: List<MonthlyExpense>) {
                monthlyExpenses.onExpensesLoaded(expenses)
            }

        })
    }

    override fun getDailyExpenses(date: String, dailyExpenses: ExpenseDSContract.LoadExpenses<List<DailyExpense>>) {
        dataSource.getDailyExpenses(date, object : ExpenseDSContract.LoadExpenses<List<DailyExpense>> {
            override fun onExpensesLoaded(expenses: List<DailyExpense>) {
                dailyExpenses.onExpensesLoaded(expenses)
            }
        })
    }

    override fun getDailyExpByCategory(date: String, category: String, expenseByCategory: ExpenseDSContract.LoadExpenses<List<DailyExpense>>) {
        dataSource.getDailyExpByCategory(date, category, object : ExpenseDSContract.LoadExpenses<List<DailyExpense>> {
            override fun onExpensesLoaded(expenses: List<DailyExpense>) {
                expenseByCategory.onExpensesLoaded(expenses)
            }
        })
    }

    override fun getMonthlyExpense(expenseId: String, monthlyExpense: ExpenseDSContract.LoadExpense<MonthlyExpense>) {
        dataSource.getMonthlyExpense(expenseId, object : ExpenseDSContract.LoadExpense<MonthlyExpense> {
            override fun onExpenseLoaded(expense: MonthlyExpense) {
                monthlyExpense.onExpenseLoaded(expense)
            }
        })
    }

    override fun getDailyExpense(expenseId: String, dailyExpense: ExpenseDSContract.LoadExpense<DailyExpense>) {
        dataSource.getDailyExpense(expenseId, object : ExpenseDSContract.LoadExpense<DailyExpense> {
            override fun onExpenseLoaded(expense: DailyExpense) {
                dailyExpense.onExpenseLoaded(expense)
            }
        })
    }

    override fun insertMonthlyExpense(monthlyExpense: MonthlyExpense) {
        dataSource.insertMonthlyExpense(monthlyExpense)
    }

    override fun insertDailyExpense(dailyExpense: DailyExpense) {
        dataSource.insertDailyExpense(dailyExpense)
    }

    override fun deleteMonthlyExpense(monthlyExpId: String) {
        dataSource.deleteMonthlyExpense(monthlyExpId)
    }

    override fun deleteDailyExpense(dailyExpId: String) {
        dataSource.deleteDailyExpense(dailyExpId)
    }

    override fun updateDailyExpense(dailyExpense: DailyExpense) {
        dataSource.updateDailyExpense(dailyExpense)
    }
}