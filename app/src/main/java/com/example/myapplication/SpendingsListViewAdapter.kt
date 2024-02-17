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

    private class ViewHolder {
        val valueTextView: TextView
        val categoryTextView: TextView
        val noteTextView: TextView

        constructor(view: View) {
            valueTextView = view.findViewById(R.id.valueTextView)
            categoryTextView = view.findViewById(R.id.categoryTextView)
            noteTextView = view.findViewById(R.id.noteTextView)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            viewHolder = ViewHolder(convertView)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        val spending = spendings[position]
        val valueText = if (spending.value > 999) {
            "999+"
        } else {
            spending.value.toString()
        }
        viewHolder.valueTextView.text = valueText

        viewHolder.categoryTextView.text = spending.category.name

        val truncatedNote = if (spending.note.length > 20) {
            spending.note.substring(0, 20) + "..."
        } else {
            spending.note
        }
        viewHolder.noteTextView.text = truncatedNote

        return convertView!!
    }
}
