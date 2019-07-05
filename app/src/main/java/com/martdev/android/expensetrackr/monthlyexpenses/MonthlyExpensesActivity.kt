package com.martdev.android.expensetrackr.monthlyexpenses

import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.martdev.android.expensetrackr.R
import com.martdev.android.expensetrackr.SingleFragmentActivity
import com.martdev.android.expensetrackr.utils.Injection

class MonthlyExpensesActivity : SingleFragmentActivity() {


    override fun createFragment(): Fragment {
        return MonthlyExpensesFragment.newInstance()
    }

    override fun setToolbarTitle(toolbar: Toolbar, actionBar: ActionBar?) {
        toolbar.title = getString(R.string.app_name)
        toolbar.titleMarginStart = 120
    }

    override fun setupPresenter(fragment: Fragment) {
        MonthlyExpensesPresenter(
                Injection.provideExpenseRepo(applicationContext),
                fragment as MonthlyExpensesFragment
        )
    }
}