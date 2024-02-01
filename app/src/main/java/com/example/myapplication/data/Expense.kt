package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "expense_table")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val value: BigDecimal,
    val note: String,
    val categories: List<Category>
)