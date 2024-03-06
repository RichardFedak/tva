package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myapplication.data.Expense
import java.util.Date

class ExpensesViewModel(application: Application): TvaBaseViewModel(application) {
    fun getExpenses(date: Date): LiveData<List<Expense>> {
        return repository.getExpenses(date)
    }
}