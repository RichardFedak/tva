package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.LocalDate

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addExpense(expense: Expense)

    @Query("SELECT * FROM expense_table ORDER BY created DESC")
    fun getExpenses(): LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE id = :id LIMIT 1")
    fun getExpense(id: Int): LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE created = :date ORDER BY created DESC")
    fun getExpensesByDate(date: LocalDate): LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE created >= :from ORDER BY created DESC")
    fun getExpensesFromDate(from: LocalDate): LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE created <= :to ORDER BY created DESC")
    fun getExpensesToDate(to: LocalDate): LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE created BETWEEN :from AND :to ORDER BY created DESC")
    fun getExpensesFromToDates(from: LocalDate, to: LocalDate): LiveData<List<Expense>>

    @Delete
    fun deleteExpense(expense: Expense)
}