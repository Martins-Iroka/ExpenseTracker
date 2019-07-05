package com.martdev.android.expensetrackr.addeditexpense

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.martdev.android.expensetrackr.R
import com.martdev.android.expensetrackr.SingleFragmentActivity
import com.martdev.android.expensetrackr.utils.Injection

class AddEditExpenseActivity : SingleFragmentActivity() {

    private var expenseId: String? = null

    companion object {
        private const val EXTRA_ID = "expenseId"

        fun newIntent(packageContext: Context, expenseId: String?): Intent {
            return Intent(packageContext, AddEditExpenseActivity::class.java).apply {
                putExtra(EXTRA_ID, expenseId)
            }
        }
    }

    override fun createFragment(): Fragment {
        expenseId = intent.getStringExtra(EXTRA_ID)
        return AddEditExpenseFragment.newInstance(expenseId)
    }

    override fun setToolbarTitle(toolbar: Toolbar, actionBar: ActionBar?) {
        actionBar?.run {
            setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp)
            setDisplayHomeAsUpEnabled(true)
        }
        if (expenseId == null) toolbar.title = getString(R.string.new_expense)
        else toolbar.title = getString(R.string.edit_expense)
    }

    override fun setupPresenter(fragment: Fragment) {
        AddEditExpensePresenter(expenseId,
                Injection.provideExpenseRepo(applicationContext),
                fragment as AddEditExpenseFragment,
                this)
    }
}