package com.martdev.android.expensetrackr.data.dao

import androidx.room.*
import com.martdev.android.expensetrackr.data.Income

@Dao
interface IncomeDao {

    @Query("SELECT * FROM income")
    fun getIncomes(): List<Income>

    @Query("SELECT * FROM income WHERE incomeId = :id")
    fun getIncome(id: String): Income

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIncome(income: Income)

    @Update
    fun updateIncome(income: Income)

    @Query("DELETE FROM income WHERE incomeId = :id")
    fun deleteIncome(id: String)
}