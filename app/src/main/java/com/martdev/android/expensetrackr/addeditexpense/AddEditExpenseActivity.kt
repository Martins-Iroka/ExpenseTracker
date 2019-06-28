package com.martdev.android.expensetrackr.addeditexpense

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import com.martdev.android.expensetrackr.SingleFragmentActivity
import com.martdev.android.expensetrackr.utils.Injection

class AddEditExpenseActivity : SingleFragmentActivity() {

    private var expenseId: String? = null

    companion object {
        private const val EXTRA_ID = "expenseId"

        fun newIntent(packageContext: Context, expenseId: String): Intent {
            val intent = Intent(packageContext, AddEditExpenseActivity::class.java)
            intent.putExtra(EXTRA_ID, expenseId)
            return intent
        }
    }

    override fun createFragment(): Fragment {
        expenseId = intent.getStringExtra(EXTRA_ID)
        return AddEditExpenseFragment.newInstance(expenseId)
    }

    override fun setToolbarTitle(actionBar: ActionBar?) {
        actionBar?.run {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = if (expenseId == null) "New Expense"
            else "Edit Expense"
        }
    }

    override fun setupPresenter(fragment: Fragment) {
        AddEditExpensePresenter(expenseId,
                Injection.provideExpenseRepo(applicationContext),
                fragment as AddEditExpenseFragment,
                this)
    }
}