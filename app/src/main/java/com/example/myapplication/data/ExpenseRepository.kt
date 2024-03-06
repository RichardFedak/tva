package com.example.myapplication.data

import androidx.lifecycle.LiveData
import java.util.Date

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    suspend fun saveExpense(expense: Expense) {
        if (expense.id == 0) {
            expenseDao.addExpense(expense)
        } else {
            expenseDao.updateExpense(expense)
        }
    }

    fun getExpense(id: Int): LiveData<Expense> {
        return expenseDao.getExpense(id)
    }

    fun getExpenses(date: Date): LiveData<List<Expense>> {
        val dateInMillis = date.time
        return expenseDao.getExpensesByDate(dateInMillis)
    }

    fun getExpenses(from: Date?, to: Date?, categories: List<Category>?): LiveData<List<ExpenseByCategory>> {
        val fromInMillis = from?.time
        val toInMillis = to?.time

        return if (categories.isNullOrEmpty()) {
            if (fromInMillis != null && toInMillis != null) {
                expenseDao.getExpensesFromToDatesByCategory(fromInMillis, toInMillis)
            } else if (toInMillis != null) {
                expenseDao.getExpensesToDateByCategory(toInMillis)
            } else if (fromInMillis != null) {
                expenseDao.getExpensesFromDateByCategory(fromInMillis)
            } else {
                expenseDao.getAllExpensesByCategory()
            }
        } else {
            if (fromInMillis != null && toInMillis != null) {
                expenseDao.getExpensesFromToDatesByCategory(fromInMillis, toInMillis, categories)
            } else if (toInMillis != null) {
                expenseDao.getExpensesToDateByCategory(toInMillis, categories)
            } else if (fromInMillis != null) {
                expenseDao.getExpensesFromDateByCategory(fromInMillis, categories)
            } else {
                expenseDao.getAllExpensesByCategory(categories)
            }
        }
    }

    suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }
}
