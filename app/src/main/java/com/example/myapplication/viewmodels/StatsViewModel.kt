package com.example.myapplication.viewmodels

import android.app.Application
import com.example.myapplication.data.Category
import com.example.myapplication.data.Expense
import java.util.Date

class StatsViewModel(application: Application): TvaBaseViewModel(application) {
    fun getExpenses(from: Date?, to: Date?, categories: List<Category>?): List<Expense> {
        val expenses = repository.getExpenses(from, to)

        if (categories.isNullOrEmpty()) {
            return expenses
        }

        return expenses.filter {
                expense -> categories.any {
                    category -> expense.category == category
                }
            }
    }
}