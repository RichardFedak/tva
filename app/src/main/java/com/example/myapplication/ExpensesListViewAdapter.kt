package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.myapplication.data.Expense

class ExpensesListViewAdapter(private val context: Context, private val expenses: ArrayList<Expense>)
    : ArrayAdapter<Expense>(context, R.layout.list_item, expenses) {
    private class ViewHolder(view: View) {
        val valueTextView: TextView
        val categoryTextView: TextView
        val noteTextView: TextView

        init {
            valueTextView = view.findViewById(R.id.valueTextView)
            categoryTextView = view.findViewById(R.id.categoryTextView)
            noteTextView = view.findViewById(R.id.noteTextView)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var newView = convertView
        val viewHolder: ViewHolder
        if (newView == null) {
            newView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            viewHolder = ViewHolder(newView)
            newView.tag = viewHolder
        } else {
            viewHolder = newView.tag as ViewHolder
        }

        val expense = expenses[position]
        val valueText = if (expense.value > 9999) {
            "9999+"
        } else {
            expense.value.toString()
        }
        viewHolder.valueTextView.text = valueText

        viewHolder.categoryTextView.text = expense.category.getName(context)

        val truncatedNote = if (expense.note.length > 20) {
            expense.note.substring(0, 20) + "..."
        } else {
            expense.note
        }
        viewHolder.noteTextView.text = truncatedNote

        return newView!!
    }
}
