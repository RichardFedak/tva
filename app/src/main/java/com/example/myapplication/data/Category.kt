package com.example.myapplication.data

import android.content.Context
import com.example.myapplication.R

enum class Category {
    Food { override fun getName(context: Context): String { return context.getString(R.string.category_food) } },
    Fun { override fun getName(context: Context): String { return context.getString(R.string.category_fun) } },
    Shopping { override fun getName(context: Context): String { return context.getString(R.string.category_shopping) } },
    Health { override fun getName(context: Context): String { return context.getString(R.string.category_health) } },
    Transport { override fun getName(context: Context): String { return context.getString(R.string.category_transport) } },
    Housing { override fun getName(context: Context): String { return context.getString(R.string.category_housing) } },
    Other { override fun getName(context: Context): String { return context.getString(R.string.category_other) } };

    abstract fun getName(context: Context): String

    companion object {
        fun getValue(name: String, context: Context): Category {
            return when(name) {
                context.getString(R.string.category_food) -> Food
                context.getString(R.string.category_fun) -> Fun
                context.getString(R.string.category_shopping) -> Shopping
                context.getString(R.string.category_health) -> Health
                context.getString(R.string.category_transport) -> Transport
                context.getString(R.string.category_housing) -> Housing
                context.getString(R.string.category_other) -> Other
                else -> throw IllegalArgumentException("Unknown category name.")
            }
        }
    }
}
