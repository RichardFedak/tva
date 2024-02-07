package com.example.myapplication.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import java.time.LocalDate
import java.util.Date

class StatsViewModel(application: Application): AndroidViewModel(application) {
//    private val repository: ExpenseRepository
//
//    init {
//        val expenseDao = ExpenseDatabase.getDatabase(application).expenseDao()
//        repository = ExpenseRepository(expenseDao)
//    }
//
//    fun getExpenses(from: Date?, to: Date?, categories: List<Category>?): LiveData<List<Expense>> {
//        val expenses = repository.getExpenses(from, to)
//
//        if (categories.isNullOrEmpty()) {
//            return expenses
//        }
//
//        return expenses.map {
//            it.filter {
//                expense -> categories.any {
//                    category -> expense.category == category
//                }
//            }
//        }
//    }
}