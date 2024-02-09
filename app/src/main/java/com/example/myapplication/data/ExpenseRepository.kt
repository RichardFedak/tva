package com.example.myapplication.data

import java.util.Date

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    fun addExpense(expense: Expense) {
        expenseDao.addExpense(expense)
    }

    fun getExpense(id: Int): List<Expense> {
        return expenseDao.getExpense(id)
    }

    fun getExpenses(date: Date): List<Expense> {
        val dateInMillis = date.time
        return expenseDao.getExpensesByDate(dateInMillis)
    }

    fun getExpenses(from: Date?, to: Date?): List<Expense> {
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
