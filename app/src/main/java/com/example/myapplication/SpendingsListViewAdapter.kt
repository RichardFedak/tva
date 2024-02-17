package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.myapplication.data.Expense

class SpendingsListViewAdapter(private val context: Context, private val spendings: ArrayList<Expense>)
    : ArrayAdapter<Expense>(context, R.layout.list_item, spendings) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item, null)

        val valueTextView: TextView = view.findViewById(R.id.valueTextView)
        val categoryTextView: TextView = view.findViewById(R.id.categoryTextView)
        val noteTextView: TextView = view.findViewById(R.id.noteTextView)

        val spending = spendings[position]
        val valueText = if (spending.value > 999) {
            "999+"
        } else {
            spending.value.toString()
        }
        valueTextView.text = valueText

        categoryTextView.text = spending.category.name

        val truncatedNote = if (spending.note.length > 20) {
            spending.note.substring(0, 20) + "..."
        } else {
            spending.note
        }
        noteTextView.text = truncatedNote

        return view
    }
}