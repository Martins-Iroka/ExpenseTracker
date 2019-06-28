package com.martdev.android.expensetrackr.addeditexpense

import android.content.Context
import android.content.Intent
import com.martdev.android.expensetrackr.BasePresenter
import com.martdev.android.expensetrackr.BaseView
import java.util.*

interface AddEditExpenseContract {

    interface View : BaseView<Presenter> {

        fun showAmount(amount: String)

        fun setCategoryId(categoryId: Int)

        fun showDate(date: String)

        fun showDescription(description: String)

        fun showDateDialog(date: Date)

        fun showExpenseListUI()

        fun showPermissionDialog()
    }

    interface Presenter : BasePresenter {

        fun getAmount(amount: String)

        fun getCategory(category: String)

        fun getCategoryId(categoryId: Int)

        fun getDate(date: Date)

        fun getDescription(description: String)

        fun openDateDialog()

        fun hasPermission(): Boolean

        fun requestPermission()

        fun permissionResult(requestCode: Int)

        fun activityResult(requestCode: Int, resultCode: Int, data: Intent?)

        fun saveExpense()

        fun cancelExpense()

        fun updateExpense()

        fun loadExpense()
    }
}