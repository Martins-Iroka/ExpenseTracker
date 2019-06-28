package com.martdev.android.expensetrackr.data.dao

import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.martdev.android.expensetrackr.data.MonthlyExpense

interface MonthlyExpDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMonthlyExpense(monthlyExp: MonthlyExpense)

    @Query("DELETE FROM monthly_exp_table WHERE month_id = :monthId")
    fun deleteMonthExpense(monthId: String)

    @Query("SELECT * FROM monthly_exp_table")
    fun getMonthlyExpenses(): List<MonthlyExpense>

    @Query("SELECT * FROM monthly_exp_table WHERE month_id = :id")
    fun getMonthlyExpense(id: String): MonthlyExpense
}