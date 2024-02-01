package com.example.myapplication.data

import androidx.lifecycle.LiveData

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    val readAllExpenses: LiveData<List<Expense>> = expenseDao.readAllExpenses()

    suspend fun addExpense(expense: Expense) {
        expenseDao.addExpense(expense)
    }
}