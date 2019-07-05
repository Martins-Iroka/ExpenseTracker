package com.martdev.android.expensetrackr.dailyexpenselist

import android.content.Intent
import com.martdev.android.expensetrackr.data.DailyExpense
import com.martdev.android.expensetrackr.data.expensedatasource.ExpenseDataSource
import java.util.*

class DailyExpensesPresenter(
        private val date: String,
        private val dataSource: ExpenseDataSource,
        private val expenseView: DailyExpensesContract.View
) : DailyExpensesContract.Presenter {

    init {
        expenseView.presenter = this
    }

    override fun loadDailyExpenses() {
        dataSource.getDailyExpenses(date, object : ExpenseDataSource.LoadExpenses<List<DailyExpense>> {
            override fun onExpensesLoaded(expenses: List<DailyExpense>) {
                if (expenses.isEmpty()) expenseView.showMessageView()
                else {
                    val linkList = LinkedList<DailyExpense>()
                    for (expense in expenses) {
                        linkList.addFirst(expense)
                    }
                    expenseView.showDailyExpenses(linkList)
                }
            }
        })
    }

    override fun loadDailyExpensesByFilter(category: String) {
        dataSource.getDailyExpByCategory(date, category, object : ExpenseDataSource.LoadExpenses<List<DailyExpense>> {
            override fun onExpensesLoaded(expenses: List<DailyExpense>) {
                if (expenses.isEmpty()) expenseView.showNoMessageForCategory(category)
                else {
                    val linkedList = LinkedList<DailyExpense>()
                    for (expense in expenses) {
                        linkedList.addFirst(expense)
                    }
                    expenseView.showDailyExpenses(linkedList)
                }
            }
        })
    }

    override fun openNewExpense() {
        expenseView.showDailyExpense()
    }

    override fun openDailyExpense(expenseId: String) {
        expenseView.showDailyExpense(expenseId)
    }

    override fun deleteDailyExpense(expenseId: String) {
        dataSource.deleteDailyExpense(expenseId)
        loadDailyExpenses()
        expenseView.showDailyExpenseDeleted()
    }

    override fun deleteDailyExpenseByCategory(category: String) {
        dataSource.deleteDailyByCategory(category)
        loadDailyExpensesByFilter(category)
        expenseView.showNoMessageForCategory(category)
    }

    override fun result(requestCode: Int, resultCode: Int, data: Intent) {

    }

    override fun start() {
        loadDailyExpenses()
    }
}