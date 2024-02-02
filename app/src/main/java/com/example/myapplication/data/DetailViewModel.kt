package com.example.myapplication.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ExpenseRepository

    init {
        val expenseDao = ExpenseDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addExpense(expense)
        }
    }

    fun getExpense(id: Int): LiveData<List<Expense>> {
        return repository.getExpense(id)
    }

    fun deleteExpense(expense: Expense) {
        return repository.deleteExpense(expense)
    }
}