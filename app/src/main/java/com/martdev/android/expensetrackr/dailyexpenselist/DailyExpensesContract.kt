package com.martdev.android.expensetrackr.dailyexpenselist

import android.content.Intent
import com.martdev.android.expensetrackr.BasePresenter
import com.martdev.android.expensetrackr.BaseView
import com.martdev.android.expensetrackr.data.DailyExpense

interface DailyExpensesContract {

    interface View : BaseView<Presenter> {

        fun showDailyExpenses(expenses: List<DailyExpense>)

        fun showDailyExpense(expenseId: String? = null)

        fun showMessageView()

        fun showNoMessageForCategory(category: String)

        fun showDailyExpenseDeleted()

        fun showExpensesByCategoryDeleted(category: String)

        fun showFilteringPopUpMenu()
    }

    interface Presenter : BasePresenter {

        fun loadDailyExpenses()

        fun loadDailyExpensesByFilter(category: String)

        fun openNewExpense()

        fun openDailyExpense(expenseId: String)

        fun deleteDailyExpense(expenseId: String)

        fun deleteDailyExpenseByCategory(category: String)

        fun result(requestCode: Int, resultCode: Int, data: Intent)
    }
}