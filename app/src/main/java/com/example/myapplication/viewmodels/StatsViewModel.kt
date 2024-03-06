package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myapplication.data.Category
import com.example.myapplication.data.ExpenseByCategory
import java.util.Date

class StatsViewModel(application: Application): TvaBaseViewModel(application) {
    private var dateFrom: Date? = null

    private var dateTo: Date? = null

    private var categories: List<Category>? = null

    fun setDateFrom(date: Date) {
        dateFrom = date
    }

    fun setDateTo(date: Date) {
        dateTo = date
    }

    fun setCategories(categories: List<Category>?) {
        this.categories = categories
    }

    fun getCategories(): List<Category>? {
        return this.categories
    }

    fun getExpenses(): LiveData<List<ExpenseByCategory>> {
        return repository.getExpenses(dateFrom, dateTo, categories)
    }

    fun resetFilter() {
        dateFrom = null
        dateTo = null
        categories = null
    }
}