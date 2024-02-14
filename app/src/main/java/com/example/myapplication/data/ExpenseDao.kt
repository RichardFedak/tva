package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExpenseDao {
    @Insert
    fun addExpense(expense: Expense)

    @Update
    fun updateExpense(expense: Expense)

    @Query("SELECT * FROM expense_table ORDER BY created DESC")
    fun getExpenses(): List<Expense>

    @Query("SELECT * FROM expense_table WHERE id = :id LIMIT 1")
    fun getExpense(id: Int): List<Expense>

    @Query("SELECT * FROM expense_table WHERE created = :dateInMillis ORDER BY created DESC")
    fun getExpensesByDate(dateInMillis: Long): List<Expense>

    @Query("SELECT * FROM expense_table WHERE created >= :fromInMillis ORDER BY created DESC")
    fun getExpensesFromDate(fromInMillis: Long): List<Expense>

    @Query("SELECT * FROM expense_table WHERE created <= :toInMillis ORDER BY created DESC")
    fun getExpensesToDate(toInMillis: Long): List<Expense>

    @Query("SELECT * FROM expense_table WHERE created BETWEEN :fromInMillis AND :toInMillis ORDER BY created DESC")
    fun getExpensesFromToDates(fromInMillis: Long, toInMillis: Long): List<Expense>

    @Delete
    fun deleteExpense(expense: Expense)
}
