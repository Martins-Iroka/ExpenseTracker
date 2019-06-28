package com.martdev.android.expensetrackr.data.dao

import android.arch.persistence.room.*
import com.martdev.android.expensetrackr.data.DailyExpense

@Dao interface DailyExpenseDao {

    @Query("SELECT * FROM expense WHERE date = :date")
    fun getDailyExpenses(date: String): List<DailyExpense>

    @Query("SELECT * FROM expense WHERE expenseId = :id")
    fun getDailyExpense(id: String): DailyExpense

    @Query("SELECT * FROM expense WHERE date = :date AND category = :category")
    fun getDailyExpByCategory(date: String, category: String): List<DailyExpense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDailyExpense(dailyExpense: DailyExpense)

    @Update
    fun updateDailyExpense(dailyExpense: DailyExpense)

    @Query("DELETE FROM expense WHERE expenseId = :id")
    fun deleteDailyExpense(id: String)
}