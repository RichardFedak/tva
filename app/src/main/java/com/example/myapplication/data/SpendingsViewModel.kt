package com.example.myapplication.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import java.time.LocalDate

class SpendingsViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ExpenseRepository

    init {
        val expenseDao = ExpenseDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
    }

    fun getExpenses(date: LocalDate): LiveData<List<Expense>> {
        return repository.getExpenses(date)
    }

    fun deleteExpense(expense: Expense) {
        return repository.deleteExpense(expense)
    }
}