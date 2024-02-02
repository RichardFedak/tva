package com.example.myapplication.data

import androidx.lifecycle.LiveData
import java.time.LocalDate

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    suspend fun addExpense(expense: Expense) {
        expenseDao.addExpense(expense)
    }

    fun getExpense(id: Int): LiveData<List<Expense>> {
        return expenseDao.getExpense(id)
    }

    fun getExpenses(date: LocalDate): LiveData<List<Expense>> {
        return expenseDao.getExpensesByDate(date)
    }

    fun getExpenses(from: LocalDate?, to: LocalDate?): LiveData<List<Expense>> {
        return if (from != null && to != null) {
            expenseDao.getExpensesFromToDates(from, to)
        } else if (to != null) {
            expenseDao.getExpensesToDate(to)
        } else if (from != null) {
            expenseDao.getExpensesFromDate(from)
        } else {
            expenseDao.getExpenses()
        }
    }

    fun deleteExpense(expense: Expense) {
        return expenseDao.deleteExpense(expense)
    }
}