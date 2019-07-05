package com.martdev.android.expensetrackr.dailyexpenselist

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.martdev.android.expensetrackr.R
import com.martdev.android.expensetrackr.addeditexpense.AddEditExpenseActivity
import com.martdev.android.expensetrackr.data.DailyExpense
import com.martdev.android.expensetrackr.utils.Utils
import java.text.DateFormat

class DailyExpensesFragment : Fragment(), DailyExpensesContract.View{

    companion object {
        private const val ARGUMENT_DATE = "Date"

        fun newInstance(date: String): DailyExpensesFragment {
            val fragment = DailyExpensesFragment()
            val arg = Bundle().apply { putString(ARGUMENT_DATE, date) }
            fragment.arguments = arg

            return fragment
        }

        private val FILTER = arrayOf(
                "Movies", "Food", "Bills", "Loan", "Rent",
                "Drinks", "Clothing", "Health Care", "Education", "HouseHold"
        )
    }

    override lateinit var presenter: DailyExpensesContract.Presenter

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessageView: TextView
    private lateinit var mAdapter: DailyExpenseAdapter
    private lateinit var mFAB: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mAdapter = DailyExpenseAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.expense_recyclerview, container, false)
        with(view) {
            mRecyclerView = findViewById(R.id.expense_recycler_view)
            mRecyclerView.run {
                adapter = mAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
            }
            mMessageView = findViewById(R.id.message_view)
            mFAB = findViewById(R.id.floatingActionButton)
            mFAB.run {
                setOnClickListener {
                    presenter.openNewExpense()
                }
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.daily_expenses_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.expense_filter -> showFilteringPopUpMenu()
        }
        return true
    }

    override fun showFilteringPopUpMenu() {
        val activity = activity ?: return
        val context = context ?: return
        PopupMenu(context, activity.findViewById(R.id.expense_filter)).apply {
            menuInflater.inflate(R.menu.filter_options, menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.movies -> presenter.loadDailyExpensesByFilter(FILTER[0])
                    R.id.food -> presenter.loadDailyExpensesByFilter(FILTER[1])
                    R.id.bills -> presenter.loadDailyExpensesByFilter(FILTER[2])
                    R.id.loan -> presenter.loadDailyExpensesByFilter(FILTER[3])
                    R.id.rent -> presenter.loadDailyExpensesByFilter(FILTER[4])
                    R.id.drinks -> presenter.loadDailyExpensesByFilter(FILTER[5])
                    R.id.clothing -> presenter.loadDailyExpensesByFilter(FILTER[6])
                    R.id.health_care -> presenter.loadDailyExpensesByFilter(FILTER[7])
                    R.id.education -> presenter.loadDailyExpensesByFilter(FILTER[8])
                    R.id.household -> presenter.loadDailyExpensesByFilter(FILTER[9])
                    else -> presenter.loadDailyExpenses()
                }
                true
            }
            show()
        }
    }

    override fun showDailyExpenses(expenses: List<DailyExpense>) {
        mAdapter.setExpenses(expenses)
        mMessageView.visibility = View.GONE
        mRecyclerView.visibility = View.VISIBLE
    }

    override fun showDailyExpense(expenseId: String?) {
        val intent = AddEditExpenseActivity.newIntent(activity!!, expenseId)
        startActivity(intent)
    }

    override fun showMessageView() {
        mRecyclerView.visibility = View.GONE
        with(mMessageView) {
            visibility = View.VISIBLE
            text = getString(R.string.no_daily_exp)
        }
    }

    override fun showNoMessageForCategory(category: String) {
        mRecyclerView.visibility = View.GONE
        with(mMessageView) {
            visibility = View.VISIBLE
            text = getString(R.string.no_category_message, category)
        }
    }

    override fun showDailyExpenseDeleted() {
        Utils.showSnackBar(mRecyclerView, "Expense deleted", Snackbar.LENGTH_LONG)
    }

    override fun showExpensesByCategoryDeleted(category: String) {
        Utils.showSnackBar(mRecyclerView, "Expenses for $category category deleted",
                Snackbar.LENGTH_LONG)
    }

    private inner class DailyExpenseHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private lateinit var dailyExpense: DailyExpense
        private var category: TextView
        private var amount: TextView
        private var date: TextView

        init {
            with(itemView) {
                category = findViewById(R.id.category)
                amount = findViewById(R.id.amount)
                date = findViewById(R.id.date)
            }
            itemView.setOnClickListener(this)
        }

        fun bind(dailyExpense: DailyExpense) {
            this.dailyExpense = dailyExpense
            category.text = dailyExpense.category
            amount.text = Utils.getCurrencyFormat(dailyExpense.amount!!)
            date.text = DateFormat.getDateInstance(DateFormat.MEDIUM).format(dailyExpense.date)
        }

        override fun onClick(v: View?) {
            presenter.openDailyExpense(dailyExpense.id)
        }
    }

    private inner class DailyExpenseAdapter(expenses: List<DailyExpense>? = null)
        : RecyclerView.Adapter<DailyExpenseHolder>() {

        private var mExpenses = expenses

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyExpenseHolder {
            val inflater = LayoutInflater.from(activity)
                    .inflate(R.layout.daily_expense_item_view, parent, false)
            return DailyExpenseHolder(inflater)
        }

        override fun getItemCount(): Int {
            return if (mExpenses == null) 0 else mExpenses!!.size
        }

        override fun onBindViewHolder(holder: DailyExpenseHolder, position: Int) {
            val expenses = mExpenses?.get(position)
            holder.bind(expenses!!)
        }

        fun setExpenses(expenses: List<DailyExpense>) {
            mExpenses = expenses
            notifyDataSetChanged()
        }
    }
}