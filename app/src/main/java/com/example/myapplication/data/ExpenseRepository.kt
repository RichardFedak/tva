package com.example.myapplication.data

import java.util.Date

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    fun saveExpense(expense: Expense) {
        if (expense.id == 0) {
            expenseDao.addExpense(expense)
        } else {
            expenseDao.updateExpense(expense)
        }

    }

    fun getExpense(id: Int): List<Expense> {
        return expenseDao.getExpense(id)
    }

    fun getExpenses(date: Date): List<Expense> {
        val dateInMillis = date.time
        return expenseDao.getExpensesByDate(dateInMillis)
    }

    fun getExpenses(from: Date?, to: Date?): List<ExpenseByCategory> {
        val fromInMillis = from?.time
        val toInMillis = to?.time

        return if (fromInMillis != null && toInMillis != null) {
            expenseDao.getExpensesFromToDatesByCategory(fromInMillis, toInMillis)
        } else if (toInMillis != null) {
            expenseDao.getExpensesToDateByCategory(toInMillis)
        } else if (fromInMillis != null) {
            expenseDao.getExpensesFromDateByCategory(fromInMillis)
        } else {
            expenseDao.getAllExpensesByCategory()
        }
    }

    fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }
}
