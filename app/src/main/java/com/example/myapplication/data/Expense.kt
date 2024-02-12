package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "expense_table")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "value")
    val value: Double = 0.0,
    @ColumnInfo(name = "note")
    val note: String = "",
    @ColumnInfo(name = "created")
    val created: Date,
    @ColumnInfo(name = "category")
    val category: Category = Category.Unset,
)