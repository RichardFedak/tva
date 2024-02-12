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
        val noteTextView: TextView = view.findViewById(R.id.noteTextView)

        val spending = spendings[position]
        valueTextView.text = spending.value.toString()
        noteTextView.text = spending.note

        return view
    }
}