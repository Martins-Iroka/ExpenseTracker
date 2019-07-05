package com.martdev.android.expensetrackr.monthlyexpenses

import com.martdev.android.expensetrackr.BasePresenter
import com.martdev.android.expensetrackr.BaseView
import com.martdev.android.expensetrackr.data.MonthlyExpense
import java.util.*

interface MonthlyExpensesContract {

    interface View : BaseView<Presenter> {

        fun showMonthlyExpenses(expenses: List<MonthlyExpense>)

        fun showNewExpenseForMonth(date: String)

        fun showEditExpenseForAMonth(date: String)

        fun showMessageView(expenses: List<MonthlyExpense>)

        fun showMonthlyCardDeleted(date: String)
    }

    interface Presenter : BasePresenter {

        fun loadMonthlyExpenses()

        fun openNewExpenseForMonth()

        fun openExpenseForMonth(date: Date)

        fun deleteMonthlyExpenses(expenseId: String, date: Date)
    }
}