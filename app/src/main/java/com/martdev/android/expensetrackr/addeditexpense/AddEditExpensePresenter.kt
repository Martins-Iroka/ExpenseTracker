package com.martdev.android.expensetrackr.addeditexpense

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.martdev.android.expensetrackr.data.DailyExpense
import com.martdev.android.expensetrackr.data.expensedatasource.ExpenseDSContract
import com.martdev.android.expensetrackr.dialog.DateDialog
import com.martdev.android.expensetrackr.utils.DateUtils
import java.util.*

class AddEditExpensePresenter(
        private val expenseId: String? = null,
        private val dataSource: ExpenseDSContract,
        private val taskView: AddEditExpenseContract.View,
        private var context: Context
) : AddEditExpenseContract.Presenter {

    private var dailyExpense: DailyExpense? = null

    init {
        taskView.presenter = this
        dailyExpense = DailyExpense()
    }

    override fun getAmount(amount: String) {
        dailyExpense?.amount = amount
    }

    override fun getCategory(category: String) {
        dailyExpense?.category = category
    }

    override fun getCategoryId(categoryId: Int) {
        dailyExpense?.categoryId = categoryId
    }

    override fun getDate(date: Date) {
        dailyExpense?.date = date
    }

    override fun getDescription(description: String) {
        dailyExpense?.description = description
    }

    override fun openDateDialog() {
        taskView.showDateDialog(dailyExpense!!.date)
    }

    override fun hasPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(context, AddEditExpenseFragment.PERMISSION[0])
        return result == PackageManager.PERMISSION_GRANTED
    }

    override fun requestPermission() {
        taskView.showPermissionDialog()
    }

    override fun permissionResult(requestCode: Int) {
        if (requestCode == AddEditExpenseFragment.REQUEST_PERMISSION)
            hasPermission()
    }

    override fun activityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == AddEditExpenseFragment.REQUEST_DATE) {
            getDate(DateDialog.getNewDate(data!!))
            val dateLong = dailyExpense?.date!!.time
            val date = DateUtils.getReadableDate(dateLong, context)
            taskView.showDate(date)
        }
    }

    override fun start() {
        if (expenseId != null) loadExpense()
    }

    override fun saveExpense() {
        if (expenseId == null) {
            if (dailyExpense?.amount == null)
                dataSource.deleteDailyExpense(dailyExpense?.id!!)
            else
                dataSource.insertDailyExpense(dailyExpense!!)
        } else updateExpense()

        taskView.showExpenseListUI()
    }

    override fun cancelExpense() {
        taskView.showExpenseListUI()
    }

    override fun updateExpense() {
        dailyExpense?.let { dataSource.updateDailyExpense(it) }
    }

    override fun loadExpense() {
        dataSource.getDailyExpense(expenseId!!, object : ExpenseDSContract.LoadExpense<DailyExpense> {
            override fun onExpenseLoaded(expense: DailyExpense) {
                dailyExpense = expense
                expense.run {
                    amount?.let { taskView.showAmount(it) }
                    categoryId?.let { taskView.setCategoryId(it) }
                    taskView.showDate(expense.date.let { DateUtils.getReadableDate(it.time, context) })
                    description?.let { taskView.showDescription(it) }
                }

//                dailyExpense = expense
//                expense.amount?.let { taskView.showAmount(it) }
//                expense.categoryId?.let { taskView.setCategoryId(it) }
//                taskView.showDate(expense.date.let { DateUtils.getReadableDate(it.time, context) })
//                expense.description?.let { taskView.showDescription(it) }
            }
        })
    }
}