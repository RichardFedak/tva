package com.example.myapplication.data

import androidx.lifecycle.LiveData
import java.util.Date

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    suspend fun addExpense(expense: Expense) {
        expenseDao.addExpense(expense)
    }

    fun getExpense(id: Int): LiveData<List<Expense>> {
        return expenseDao.getExpense(id)
    }

    fun getExpenses(date: Date): LiveData<List<Expense>> {
        val dateInMillis = date.time
        return expenseDao.getExpensesByDate(dateInMillis)
    }

    fun getExpenses(from: Date?, to: Date?): LiveData<List<Expense>> {
        val fromInMillis = from?.time
        val toInMillis = to?.time

        return if (fromInMillis != null && toInMillis != null) {
            expenseDao.getExpensesFromToDates(fromInMillis, toInMillis)
        } else if (toInMillis != null) {
            expenseDao.getExpensesToDate(toInMillis)
        } else if (fromInMillis != null) {
            expenseDao.getExpensesFromDate(fromInMillis)
        } else {
            expenseDao.getExpenses()
        }
    }

    fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }
}
