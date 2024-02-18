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

    @Query("SELECT category, SUM(value) AS totalExpense, COUNT(*) AS expensesCount FROM expense_table GROUP BY category")
    fun getAllExpensesByCategory(): List<ExpenseByCategory>
    @Query("SELECT category, SUM(value) AS totalExpense, COUNT(*) AS expensesCount FROM expense_table WHERE created >= :fromInMillis GROUP BY category")
    fun getExpensesFromDateByCategory(fromInMillis: Long): List<ExpenseByCategory>

    @Query("SELECT category, SUM(value) AS totalExpense, COUNT(*) AS expensesCount FROM expense_table WHERE created <= :toInMillis GROUP BY category")
    fun getExpensesToDateByCategory(toInMillis: Long): List<ExpenseByCategory>

    @Query("SELECT category, SUM(value) AS totalExpense, COUNT(*) AS expensesCount FROM expense_table WHERE created BETWEEN :fromInMillis AND :toInMillis GROUP BY category")
    fun getExpensesFromToDatesByCategory(fromInMillis: Long, toInMillis: Long): List<ExpenseByCategory>

    @Delete
    fun deleteExpense(expense: Expense)
}
