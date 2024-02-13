package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.Expense
import java.util.Date

class DetailViewModel(application: Application): TvaBaseViewModel(application) {
    /* lastDate property is used to go back to Spendings after saving spending detail
       (we need to remember last date, we cannot take it from _selectedSpending because after saving
       it will be set to null) */
    var lastDate: Date = Date()

    /* Setting _selectedSpending (to not null instance) will navigate user to SpendingDetail,
     * setting _selectedSpending to null will close SpendingDetail (will return user
     * back to Spendings) */
    private val _selectedSpending = MutableLiveData<Expense?>()

    val selectedSpending: LiveData<Expense?> get() = _selectedSpending

    fun setSelectedSpending(spending: Expense?) {
        _selectedSpending.value = spending
        if (spending != null) {
            lastDate = spending.created
        }
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