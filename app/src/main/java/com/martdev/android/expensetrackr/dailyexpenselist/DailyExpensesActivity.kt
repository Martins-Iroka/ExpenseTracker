package com.martdev.android.expensetrackr.dailyexpenselist

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.martdev.android.expensetrackr.R
import com.martdev.android.expensetrackr.SingleFragmentActivity
import com.martdev.android.expensetrackr.utils.Injection

class DailyExpensesActivity : SingleFragmentActivity() {

    companion object {
        private const val EXTRA_DATE = "com.martdev.android.expensetrackr.dailyexpenselist.date"

        fun newIntent(packageContext: Context, date: String): Intent {

            return Intent(packageContext, DailyExpensesActivity::class.java).apply {
                putExtra(EXTRA_DATE, date)
            }
        }
    }

    private lateinit var date: String

    override fun createFragment(): Fragment {
        date = intent.getStringExtra(EXTRA_DATE)
        return DailyExpensesFragment.newInstance(date)
    }

    override fun setToolbarTitle(toolbar: Toolbar, actionBar: ActionBar?) {
        actionBar?.run {
            setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.title = getString(R.string.my_expense)
    }

    override fun setupPresenter(fragment: Fragment) {
        DailyExpensesPresenter(date,
                Injection.provideExpenseRepo(applicationContext),
                fragment as DailyExpensesFragment)
    }
}