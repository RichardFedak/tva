package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Expense::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ExpenseDatabase: RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}