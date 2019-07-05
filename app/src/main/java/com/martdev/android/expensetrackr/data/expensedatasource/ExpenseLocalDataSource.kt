package com.martdev.android.expensetrackr.data.expensedatasource

import com.martdev.android.expensetrackr.data.DailyExpense
import com.martdev.android.expensetrackr.data.MonthlyExpense
import com.martdev.android.expensetrackr.data.dao.DailyExpenseDao
import com.martdev.android.expensetrackr.data.dao.MonthlyExpDao
import com.martdev.android.expensetrackr.utils.AppExecutors

class ExpenseLocalDataSource(
        private val appExecutors: AppExecutors,
        private val monthlyExpDao: MonthlyExpDao,
        private val dailyExpenseDao: DailyExpenseDao
) : ExpenseDataSource {

    companion object {
        private var INSTANCE: ExpenseDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors,
                        monthlyExpDao: MonthlyExpDao,
                        dailyExpenseDao: DailyExpenseDao): ExpenseDataSource {
            if (INSTANCE == null) {
                synchronized(ExpenseLocalDataSource::javaClass) {
                    INSTANCE = ExpenseLocalDataSource(appExecutors, monthlyExpDao, dailyExpenseDao)
                }
            }

            return INSTANCE!!
        }
    }

    override fun getMonthlyExpenses(monthlyExpenses: ExpenseDataSource.LoadExpenses<List<MonthlyExpense>>) {
        appExecutors.diskIO.execute{
            val expenses = monthlyExpDao.getMonthlyExpenses()
            appExecutors.mainThread.execute {
                monthlyExpenses.onExpensesLoaded(expenses)
            }
        }
    }

    override fun getDailyExpenses(date: String, dailyExpenses: ExpenseDataSource.LoadExpenses<List<DailyExpense>>) {
        appExecutors.diskIO.execute {
            val expenses = dailyExpenseDao.getDailyExpenses(date)
            appExecutors.mainThread.execute {
                dailyExpenses.onExpensesLoaded(expenses)
            }
        }
    }

    override fun getDailyExpByCategory(date: String, category: String, expenseByCategory: ExpenseDataSource.LoadExpenses<List<DailyExpense>>) {
        appExecutors.diskIO.execute {
            val categoryName = dailyExpenseDao.getDailyExpByCategory(date, category)
            appExecutors.mainThread.execute {
                expenseByCategory.onExpensesLoaded(categoryName)
            }
        }
    }

    override fun getMonthlyExpense(expenseId: String, monthlyExpense: ExpenseDataSource.LoadExpense<MonthlyExpense>) {
        appExecutors.diskIO.execute {
            val expense = monthlyExpDao.getMonthlyExpense(expenseId)
            appExecutors.mainThread.execute {
                monthlyExpense.onExpenseLoaded(expense)
            }
        }
    }

    override fun getDailyExpense(expenseId: String, dailyExpense: ExpenseDataSource.LoadExpense<DailyExpense>) {
        appExecutors.diskIO.execute {
            val expense = dailyExpenseDao.getDailyExpense(expenseId)
            appExecutors.mainThread.execute {
                dailyExpense.onExpenseLoaded(expense)
            }
        }
    }

    override fun insertMonthlyExpense(monthlyExpense: MonthlyExpense) {
        appExecutors.diskIO.execute {
            monthlyExpDao.insertMonthlyExpense(monthlyExpense)
        }
    }

    override fun insertDailyExpense(dailyExpense: DailyExpense) {
        appExecutors.diskIO.execute {
            dailyExpenseDao.insertDailyExpense(dailyExpense)
        }
    }

    override fun deleteMonthlyExpense(monthlyExpId: String) {
        appExecutors.diskIO.execute {
            monthlyExpDao.deleteMonthExpense(monthlyExpId)
        }
    }

    override fun deleteDailyExpense(dailyExpId: String) {
        appExecutors.diskIO.execute {
            dailyExpenseDao.deleteDailyExpense(dailyExpId)
        }
    }

    override fun deleteDailyByCategory(category: String) {
        appExecutors.diskIO.execute {
            dailyExpenseDao.deleteDailyExpenseByCategory(category)
        }
    }

    override fun deleteDailyExpensesByDate(date: String) {
        appExecutors.diskIO.execute {
            dailyExpenseDao.deleteDailyExpenseByDate(date)
        }
    }

    override fun updateDailyExpense(dailyExpense: DailyExpense) {
        appExecutors.diskIO.execute {
            dailyExpenseDao.updateDailyExpense(dailyExpense)
        }
    }
}