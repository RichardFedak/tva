package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.Expense

class DetailViewModel(application: Application): TvaBaseViewModel(application) {
    private val _selectedSpending = MutableLiveData<Expense>()

    val selectedSpending: LiveData<Expense> get() = _selectedSpending

    fun setSelectedSpending(spending: Expense) {
        _selectedSpending.value = spending
    }

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