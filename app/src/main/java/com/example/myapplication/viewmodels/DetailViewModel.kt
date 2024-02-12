package com.example.myapplication.viewmodels

import android.app.Application
import com.example.myapplication.data.Expense

class DetailViewModel(application: Application): TvaBaseViewModel(application) {
    fun addExpense(expense: Expense) {
        repository.addExpense(expense)
    }

    fun getExpense(id: Int): List<Expense> {
        return repository.getExpense(id)
    }

    fun deleteExpense(expense: Expense) {
        return repository.deleteExpense(expense)
    }
}