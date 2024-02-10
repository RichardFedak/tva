package com.example.myapplication

import CategorySelectionDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.data.Category


class Stats : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stats, container, false)

        val statsDateFromFilter = view.findViewById<TextView>(R.id.statsDateFromFilter)
        val statsDateToFilter = view.findViewById<TextView>(R.id.statsDateToFilter)
        val statsButtonFilter = view.findViewById<Button>(R.id.statsButtonFilter)
        val selectCategoriesButton = view.findViewById<Button>(R.id.selectCategoriesButton)

        selectCategoriesButton.setOnClickListener {
            showCategorySelectionDialog()
        }


        statsDateFromFilter.setOnClickListener {
            showDatePicker(statsDateFromFilter)
        }

        statsDateToFilter.setOnClickListener {
            showDatePicker(statsDateToFilter)
        }

        statsButtonFilter.setOnClickListener{
            Toast.makeText(requireContext(), "TODO", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun showDatePicker(textView: TextView) {
        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH)
        val dayOfMonth = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // TODO - UPDATE VIEWMODEL PROPERTIES (FROM & TO) FILTER VALUES
                textView.text = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            },
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.show()
    }

    private fun showCategorySelectionDialog() {
        val categories = Category.values().map { it.name }.toTypedArray()
        val selectedCategories = listOf<String>() // Initialize with the selected categories
        val dialog = CategorySelectionDialog(requireContext(), categories, selectedCategories)
        dialog.setOnConfirmClickListener {
            // TODO - USE VIEWMODEL TO STORE SELECTED CATEGORIES
        }
        dialog.show()
    }

}