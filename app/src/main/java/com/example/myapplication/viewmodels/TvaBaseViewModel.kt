package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.example.myapplication.data.ExpenseDatabase
import com.example.myapplication.data.ExpenseRepository

abstract class TvaBaseViewModel(application: Application): AndroidViewModel(application) {
    protected val repository: ExpenseRepository

    init {
        val db = Room.databaseBuilder(
            application,
            ExpenseDatabase::class.java, "expenses_db"
        )
        .build()

        val expenseDao = db.expenseDao()

        repository = ExpenseRepository(expenseDao)
    }
}