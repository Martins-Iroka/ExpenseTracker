package com.martdev.android.expensetrackr.monthlyexpenses


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.martdev.android.expensetrackr.R
import com.martdev.android.expensetrackr.dailyexpenselist.DailyExpensesActivity
import com.martdev.android.expensetrackr.data.MonthlyExpense
import com.martdev.android.expensetrackr.utils.Utils
import java.util.*

class MonthlyExpensesFragment : Fragment(), MonthlyExpensesContract.View {

    companion object {

        fun newInstance(): MonthlyExpensesFragment {
            return MonthlyExpensesFragment()
        }
    }

    //TODO you need to checkMonth function and finish up

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessageView: TextView
    private lateinit var mAdapter: MonthlyExpensesAdapter
    private lateinit var mFAB: FloatingActionButton

    override lateinit var presenter: MonthlyExpensesContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = MonthlyExpensesAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.expense_recyclerview, container, false)

        with(view) {
            mRecyclerView = findViewById(R.id.expense_recycler_view)
            mRecyclerView.run {
                adapter = mAdapter
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(activity, 2)
            }
            mMessageView = findViewById(R.id.message_view)
            mFAB = findViewById(R.id.floatingActionButton)
            mFAB.run {
                setOnClickListener {
                        presenter.openNewExpenseForMonth()
                }
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun showMonthlyExpenses(expenses: List<MonthlyExpense>) {
        mAdapter.setExpenses(expenses)
        mMessageView.visibility = View.GONE
        mRecyclerView.visibility = View.VISIBLE
    }

    override fun showNewExpenseForMonth(date: String) {
        DailyExpensesActivity.newIntent(activity!!, date).apply {
            startActivity(this)
        }
    }

    override fun showEditExpenseForAMonth(date: String) {
        DailyExpensesActivity.newIntent(activity!!, date).apply {
            startActivity(this)
        }
    }

    override fun showMessageView(expenses: List<MonthlyExpense>) {
        mAdapter.setExpenses(expenses)
        mRecyclerView.visibility = View.GONE
        with(mMessageView) {
            visibility = View.VISIBLE
            text = "No expense for any month"
        }
    }

    override fun showMonthlyCardDeleted(date: String) {
        Utils.showSnackBar(mRecyclerView, "Expenses for $date deleted.",
                Snackbar.LENGTH_LONG)
    }

    private inner class MonthlyExpenseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val dateView: TextView
        private val totalExpenses: TextView
        private val menuButton: Button

        init {
            with(itemView) {
                dateView = findViewById(R.id.date_view)
                totalExpenses = findViewById(R.id.total_expenses)
                menuButton = findViewById(R.id.pop_menu)
            }
        }

        fun bind(expense: MonthlyExpense?) {
            dateView.text = Utils.getDateFormat(expense?.date!!)

            menuButton.setOnClickListener {
                PopupMenu(activity, menuButton).apply {
                    inflate(R.menu.pop_menu_view)
                    setOnMenuItemClickListener { menuItem ->
                        when(menuItem.itemId) {
                            R.id.add_expense -> presenter.openExpenseForMonth(expense.date!!)
                            R.id.delete_expense -> presenter.deleteMonthlyExpenses(expense.id, expense.date!!)
                        }
                        true
                    }
                    show()
                }
            }
        }
    }

    private inner class MonthlyExpensesAdapter(expenses: List<MonthlyExpense>? = null)
        : RecyclerView.Adapter<MonthlyExpenseHolder>() {

        private var mExpenses = expenses

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthlyExpenseHolder {
            val inflater = LayoutInflater.from(activity)
                    .inflate(R.layout.monthly_item_view, parent, false)
            return MonthlyExpenseHolder(inflater)
        }

        override fun getItemCount(): Int {
            return if (mExpenses == null) 0 else mExpenses!!.size
        }

        override fun onBindViewHolder(holder: MonthlyExpenseHolder, position: Int) {
            val expense = mExpenses?.get(position)
            holder.bind(expense)
        }

        fun setExpenses(expenses: List<MonthlyExpense>) {
            mExpenses = expenses
            notifyDataSetChanged()
        }

        fun checkMonth(): Boolean {
            val calendar = Calendar.getInstance()
            val calendar2 = Calendar.getInstance()

            if (mExpenses?.isNotEmpty()!!) {

                for (monthlyExpense in mExpenses!!) {
                    calendar.time = monthlyExpense.date
                }

                val currentMonth = calendar2.get(Calendar.MONTH)
                val month = calendar.get(Calendar.MONTH)

                if (currentMonth == month) {
                    return false
                } else if (currentMonth > month) {
                    return true
                }
            }
            return true
        }
    }
}