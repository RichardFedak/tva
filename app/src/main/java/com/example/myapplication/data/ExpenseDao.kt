package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExpenseDao {
    @Insert
    suspend fun addExpense(expense: Expense)

    @Query("SELECT * FROM expense_table ORDER BY created DESC")
    fun getExpenses(): LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE id = :id LIMIT 1")
    fun getExpense(id: Int): LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE created = :dateInMillis ORDER BY created DESC")
    fun getExpensesByDate(dateInMillis: Long): LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE created >= :fromInMillis ORDER BY created DESC")
    fun getExpensesFromDate(fromInMillis: Long): LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE created <= :toInMillis ORDER BY created DESC")
    fun getExpensesToDate(toInMillis: Long): LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE created BETWEEN :fromInMillis AND :toInMillis ORDER BY created DESC")
    fun getExpensesFromToDates(fromInMillis: Long, toInMillis: Long): LiveData<List<Expense>>

    @Delete
    fun deleteExpense(expense: Expense)
}
