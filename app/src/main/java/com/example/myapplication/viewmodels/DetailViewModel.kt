package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.Expense
import java.util.Date

class DetailViewModel(application: Application): TvaBaseViewModel(application) {
    /* lastDate property is used to go back to Expenses after saving expense detail
       (we need to remember last date, we cannot take it from _selectedExpense because after saving
       it will be set to null) */
    var lastDate: Date = Date()

    /* Setting _selectedExpense (to not null instance) will navigate user to ExpenseDetail,
     * setting _selectedExpense to null will close ExpenseDetail (will return user
     * back to Expenses) */
    private val _selectedExpense = MutableLiveData<Expense?>()

    val selectedExpense: LiveData<Expense?> get() = _selectedExpense

    fun setSelectedExpense(expense: Expense?) {
        _selectedExpense.value = expense
        if (expense != null) {
            lastDate = expense.created
        }
    }

    fun saveExpense(expense: Expense) {
        repository.saveExpense(expense)
    }

    fun getExpense(id: Int): List<Expense> {
        return repository.getExpense(id)
    }

    fun deleteExpense() {
        val expense = selectedExpense.value
        if (expense != null && expense.id != 0) {
            repository.deleteExpense(expense)
        }
    }
}