package com.martdev.android.expensetrackr.addeditexpense

import android.content.Context
import com.martdev.android.expensetrackr.dailyexpenselist.DailyExpensesContract
import com.martdev.android.expensetrackr.data.DailyExpense
import com.martdev.android.expensetrackr.data.expensedatasource.ExpenseDataSource
import com.martdev.android.expensetrackr.data.expensedatasource.ExpenseRepo
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class AddEditExpensePresenterTest {

    @Mock private lateinit var expenseRepo: ExpenseRepo

    @Mock private lateinit var dataSource: ExpenseDataSource

    @Mock private lateinit var activity: AddEditExpenseActivity

    @Mock private lateinit var view: AddEditExpenseContract.View

    @Mock private lateinit var context: Context

    @Captor private lateinit var getDailyExpense: ArgumentCaptor<ExpenseDataSource.LoadExpense<DailyExpense>>

    private lateinit var presenter: AddEditExpensePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = AddEditExpensePresenter(
                dataSource = expenseRepo, taskView = view, context = context
        )
        expenseRepo = ExpenseRepo.getInstance(dataSource)
    }

    @Test
    fun getAmount() {
    }

    @Test
    fun getCategory() {
    }

    @Test
    fun getCategoryId() {
    }

    @Test
    fun getDate() {
    }

    @Test
    fun getDescription() {
    }

    @Test
    fun openDateDialog() {
    }

    @Test
    fun hasPermission() {
    }

    @Test
    fun requestPermission() {
    }

    @Test
    fun permissionResult() {
    }

    @Test
    fun activityResult() {
    }

    @Test
    fun createPresenter() {

        verify(view).presenter = presenter
    }

    @Test
    fun saveExpense() {

        with(expenseRepo) {
            val dailyExpense = DailyExpense()
            insertDailyExpense(dailyExpense)

            verify(this).insertDailyExpense(dailyExpense)
        }
    }

    @Test
    fun cancelExpense() {
    }

    @Test
    fun updateExpense() {
    }

    @Test
    fun loadExpense() {
    }
}