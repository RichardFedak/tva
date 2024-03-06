package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExpenseDao {
    @Insert
    suspend fun addExpense(expense: Expense)

    @Update
    suspend fun updateExpense(expense: Expense)

    @Query("SELECT * FROM expense_table ORDER BY created DESC")
    fun getExpenses(): LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE id = :id LIMIT 1")
    fun getExpense(id: Int): LiveData<Expense>

    @Query("SELECT * FROM expense_table WHERE created = :dateInMillis ORDER BY created DESC")
    fun getExpensesByDate(dateInMillis: Long): LiveData<List<Expense>>

    @Query("SELECT category, SUM(value) AS totalExpense, COUNT(*) AS expensesCount FROM expense_table GROUP BY category")
    fun getAllExpensesByCategory(): LiveData<List<ExpenseByCategory>>
    @Query("SELECT category, SUM(value) AS totalExpense, COUNT(*) AS expensesCount FROM expense_table WHERE created >= :fromInMillis GROUP BY category")
    fun getExpensesFromDateByCategory(fromInMillis: Long): LiveData<List<ExpenseByCategory>>

    @Query("SELECT category, SUM(value) AS totalExpense, COUNT(*) AS expensesCount FROM expense_table WHERE created <= :toInMillis GROUP BY category")
    fun getExpensesToDateByCategory(toInMillis: Long): LiveData<List<ExpenseByCategory>>

    @Query("SELECT category, SUM(value) AS totalExpense, COUNT(*) AS expensesCount FROM expense_table WHERE created BETWEEN :fromInMillis AND :toInMillis GROUP BY category")
    fun getExpensesFromToDatesByCategory(fromInMillis: Long, toInMillis: Long): LiveData<List<ExpenseByCategory>>

    @Query("SELECT category, SUM(value) AS totalExpense, COUNT(*) AS expensesCount FROM expense_table WHERE category IN (:categories) GROUP BY category")
    fun getAllExpensesByCategory(categories: List<Category>): LiveData<List<ExpenseByCategory>>

    @Query("SELECT category, SUM(value) AS totalExpense, COUNT(*) AS expensesCount FROM expense_table WHERE created >= :fromInMillis AND category IN (:categories) GROUP BY category")
    fun getExpensesFromDateByCategory(fromInMillis: Long, categories: List<Category>): LiveData<List<ExpenseByCategory>>

    @Query("SELECT category, SUM(value) AS totalExpense, COUNT(*) AS expensesCount FROM expense_table WHERE created AND category IN (:categories) <= :toInMillis GROUP BY category")
    fun getExpensesToDateByCategory(toInMillis: Long, categories: List<Category>): LiveData<List<ExpenseByCategory>>

    @Query("SELECT category, SUM(value) AS totalExpense, COUNT(*) AS expensesCount FROM expense_table WHERE (created BETWEEN :fromInMillis AND :toInMillis) AND (category IN (:categories)) GROUP BY category")
    fun getExpensesFromToDatesByCategory(fromInMillis: Long, toInMillis: Long, categories: List<Category>): LiveData<List<ExpenseByCategory>>

    @Delete
    suspend fun deleteExpense(expense: Expense)
}
