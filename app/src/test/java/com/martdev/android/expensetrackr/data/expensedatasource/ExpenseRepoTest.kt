package com.martdev.android.expensetrackr.data.expensedatasource

import com.martdev.android.expensetrackr.data.DailyExpense
import com.martdev.android.expensetrackr.data.MonthlyExpense
import org.junit.Before
import org.junit.Test
import org.mockito.*

import org.mockito.Mockito.any
import org.mockito.Mockito.verify
import java.util.*

class ExpenseRepoTest {

    private lateinit var expenseRepo: ExpenseRepo

    private val MONTHLY_EXPENSES = arrayListOf(MonthlyExpense(date = Date()), MonthlyExpense(date = Date()))
//    private val DAILY_EXPENSES = arrayListOf(DailyExpense(), DailyExpense())

    @Mock private lateinit var dataSource: ExpenseDataSource
    @Mock private lateinit var loadMonthlyExpenses:
            ExpenseDataSource.LoadExpenses<List<MonthlyExpense>>
    @Mock private lateinit var loadDailyExpenses:
            ExpenseDataSource.LoadExpenses<List<DailyExpense>>
    @Mock private lateinit var getDailyExpense: ExpenseDataSource.LoadExpense<DailyExpense>

    @Captor private lateinit var monthlyExpenses: ArgumentCaptor<ExpenseDataSource.LoadExpenses<List<MonthlyExpense>>>
    @Captor private lateinit var dailyExpenses: ArgumentCaptor<ExpenseDataSource.LoadExpenses<List<DailyExpense>>>
    @Captor private lateinit var dailyExpense: ArgumentCaptor<ExpenseDataSource.LoadExpense<DailyExpense>>

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        expenseRepo = ExpenseRepo.getInstance(dataSource)
    }

    @Test
    fun getMonthlyExpenses() {

        dataSource.getMonthlyExpenses(loadMonthlyExpenses)

        verify(dataSource).getMonthlyExpenses(ArgumentMatchers.any<ExpenseDataSource.LoadExpenses<List<MonthlyExpense>>>())

    }

    @Test
    fun getDailyExpenses() {
    }

    @Test
    fun getDailyExpByCategory() {
    }

    @Test
    fun getMonthlyExpense() {
    }

    @Test
    fun getDailyExpense() {
    }

    @Test
    fun insertMonthlyExpense() {

        val monthlyExpense = MonthlyExpense()
        expenseRepo.insertMonthlyExpense(monthlyExpense)
        verify(dataSource).insertMonthlyExpense(monthlyExpense)
    }

    @Test
    fun insertDailyExpense() {

        val dailyExpense = DailyExpense()
        expenseRepo.insertDailyExpense(dailyExpense)
        verify(dataSource).insertDailyExpense(dailyExpense)
    }

    @Test
    fun deleteMonthlyExpense() {
        val monthlyExpense = MonthlyExpense(date = Date())

        expenseRepo.insertMonthlyExpense(monthlyExpense)
        expenseRepo.deleteMonthlyExpense(monthlyExpense.id)
        verify(dataSource).deleteMonthlyExpense(monthlyExpense.id)

    }

    @Test
    fun deleteDailyExpense() {

        with(expenseRepo) {
            val dailyExpense = DailyExpense()
            insertDailyExpense(dailyExpense)
            deleteDailyExpense(dailyExpense.id)

            verify(dataSource).deleteDailyExpense(dailyExpense.id)
        }
    }

    @Test
    fun deleteDailyByCategory() {

        with(expenseRepo) {
            val dailyExpense = DailyExpense(category = "Rent")
            insertDailyExpense(dailyExpense)
            deleteDailyByCategory(dailyExpense.category!!)

            verify(dataSource).deleteDailyByCategory(dailyExpense.category!!)
        }
    }

    @Test
    fun deleteDailyExpensesByDate() {

        with(expenseRepo) {
            val dailyExpense = DailyExpense(dateString = "May, 1914")
            insertDailyExpense(dailyExpense)
            deleteDailyExpensesByDate(dailyExpense.dateString!!)

            verify(dataSource).deleteDailyExpensesByDate(dailyExpense.dateString!!)
        }
    }

    @Test
    fun updateDailyExpense() {

        with(expenseRepo) {
            val dailyExpense = DailyExpense()
            insertDailyExpense(dailyExpense)
            dailyExpense.category = "Food"
            updateDailyExpense(dailyExpense)

            verify(dataSource).updateDailyExpense(dailyExpense)
        }
    }
}