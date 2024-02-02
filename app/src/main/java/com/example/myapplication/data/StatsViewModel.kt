package com.example.myapplication.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import java.time.LocalDate

class StatsViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ExpenseRepository

    init {
        val expenseDao = ExpenseDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
    }

    fun getExpenses(from: LocalDate?, to: LocalDate?, categories: List<Category>?): LiveData<List<Expense>> {
        val expenses = repository.getExpenses(from, to)

        if (categories.isNullOrEmpty()) {
            return expenses
        }

        return expenses.map {
            it.filter {
                expense -> expense.categories.any {
                    category -> category in categories
                }
            }
        }
    }
}