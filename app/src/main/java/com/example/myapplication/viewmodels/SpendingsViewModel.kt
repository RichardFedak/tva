package com.example.myapplication.viewmodels

import android.app.Application
import com.example.myapplication.data.Expense
import java.util.Date

class SpendingsViewModel(application: Application): TvaBaseViewModel(application) {
    fun getExpenses(date: Date): List<Expense> {
        return repository.getExpenses(date)
    }

    fun deleteExpense(expense: Expense) {
        return repository.deleteExpense(expense)
    }
}