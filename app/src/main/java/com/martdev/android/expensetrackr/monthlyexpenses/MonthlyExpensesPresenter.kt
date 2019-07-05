package com.martdev.android.expensetrackr.monthlyexpenses

import com.martdev.android.expensetrackr.data.MonthlyExpense
import com.martdev.android.expensetrackr.data.expensedatasource.ExpenseDataSource
import com.martdev.android.expensetrackr.utils.Utils
import java.util.*

class MonthlyExpensesPresenter(
        private val dataSource: ExpenseDataSource,
        private val expenseView: MonthlyExpensesContract.View
) : MonthlyExpensesContract.Presenter {

    private var monthlyExpense: MonthlyExpense

    init {
        expenseView.presenter = this
        monthlyExpense = MonthlyExpense()
    }

    override fun loadMonthlyExpenses() {
        dataSource.getMonthlyExpenses(object : ExpenseDataSource.LoadExpenses<List<MonthlyExpense>> {
            override fun onExpensesLoaded(expenses: List<MonthlyExpense>) {
                if (expenses.isEmpty()) expenseView.showMessageView(expenses)
                else {
                    val linkList = LinkedList<MonthlyExpense>()
                    for (monthlyExpense in expenses) {
                        linkList.addFirst(monthlyExpense)
                    }
                    expenseView.showMonthlyExpenses(linkList)
                }
            }
        })
    }

    override fun openNewExpenseForMonth() {
        val calendar = Calendar.getInstance()
        monthlyExpense.date = calendar.time
        val dateString = Utils.getDateFormat(monthlyExpense.date!!)
        dataSource.insertMonthlyExpense(monthlyExpense)
        expenseView.showNewExpenseForMonth(dateString)
    }

    override fun openExpenseForMonth(date: Date) {
        val dateString = Utils.getDateFormat(date)
        expenseView.showEditExpenseForAMonth(dateString)
    }

    override fun deleteMonthlyExpenses(expenseId: String, date: Date) {
        val dateString = Utils.getDateFormat(date)
        dataSource.deleteMonthlyExpense(expenseId)
        dataSource.deleteDailyExpensesByDate(dateString)
        loadMonthlyExpenses()
        expenseView.showMonthlyCardDeleted(dateString)
    }

    override fun start() {
        loadMonthlyExpenses()
    }
}