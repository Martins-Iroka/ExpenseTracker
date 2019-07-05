package com.martdev.android.expensetrackr.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.martdev.android.expensetrackr.data.MonthlyExpense

@Dao
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