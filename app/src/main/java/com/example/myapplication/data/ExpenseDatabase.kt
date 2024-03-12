package com.example.myapplication.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Expense::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ExpenseDatabase: RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        private const val db_name = "expenses_db"
        @Volatile private var instance: ExpenseDatabase? = null

        private fun createInstance(application: Application): ExpenseDatabase {
            return databaseBuilder<ExpenseDatabase>(
                application,
                ExpenseDatabase::class.java, db_name
            ).build()
        }

        fun getInstance(application: Application) =
            instance ?: synchronized(this) {
                instance ?: createInstance(application).also { instance = it }
            }
    }
}
