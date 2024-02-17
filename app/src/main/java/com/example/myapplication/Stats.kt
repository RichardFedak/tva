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
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.Category
import com.example.myapplication.data.Expense
import com.example.myapplication.viewmodels.SpendingsViewModel
import com.example.myapplication.viewmodels.StatsViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.Date


class Stats : Fragment() {
    private lateinit var statsViewModel: StatsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statsViewModel = ViewModelProvider(this)[StatsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stats, container, false)

        val statsDateFromFilter = view.findViewById<TextView>(R.id.statsDateFromFilter)
        val statsDateToFilter = view.findViewById<TextView>(R.id.statsDateToFilter)
        val statsButtonFilter = view.findViewById<Button>(R.id.statsButtonFilter)
        val statsButtonReset = view.findViewById<Button>(R.id.statsResetButton)
        val selectCategoriesButton = view.findViewById<Button>(R.id.selectCategoriesButton)
        val selectedCategoriesChipGroup = view.findViewById<ChipGroup>(R.id.selectedCategoriesChipGroup)

        selectCategoriesButton.setOnClickListener {
            showCategorySelectionDialog(selectedCategoriesChipGroup)
        }


        statsDateFromFilter.setOnClickListener {
            showDatePicker(statsDateFromFilter, isFromDate = true)
        }

        statsDateToFilter.setOnClickListener {
            showDatePicker(statsDateToFilter, isFromDate = false)
        }

        val expensesListView = view.findViewById<ListView>(R.id.expensesListView)

        val expensesAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1)

        expensesListView.adapter = expensesAdapter

        statsButtonFilter.setOnClickListener{
            val expenses = statsViewModel.getExpenses()

            expensesAdapter.clear()

            for(expense in expenses){
                expensesAdapter.add(expense.category.toString() + ": " + expense.totalExpense)
            }
        }

        statsButtonReset.setOnClickListener{
            statsViewModel.resetFilter()
            statsDateFromFilter.text = getString(R.string.stats_filter_from)
            statsDateToFilter.text = getString(R.string.stats_filter_to)
            selectedCategoriesChipGroup.removeAllViews()
            selectedCategoriesChipGroup.visibility = View.GONE
        }

        return view
    }

    private fun showDatePicker(textView: TextView, isFromDate: Boolean) {
        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH)
        val dayOfMonth = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = java.util.Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDayOfMonth)
                }.time

                textView.text = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"

                if (isFromDate) {
                    statsViewModel.setDateFrom(selectedDate)
                } else {
                    statsViewModel.setDateTo(selectedDate)
                }
            },
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.show()
    }

    private fun showCategorySelectionDialog(selectedCategoriesChipGroup : ChipGroup) {
        val categories = Category.values().map { it.name }.toTypedArray()
        val selectedCategories = statsViewModel.getCategories()?.map { it.name } ?: emptyList()
        val dialog = CategorySelectionDialog(requireContext(), categories, selectedCategories)

        dialog.setOnConfirmClickListener { selectedCategories ->
            val selectedCategoriesList = selectedCategories.map { Category.valueOf(it) }
            statsViewModel.setCategories(selectedCategoriesList)

            selectedCategoriesChipGroup.removeAllViews()
            // Add chips for selected categories
            for (category in selectedCategories) {
                val chip = Chip(requireContext())
                chip.text = category
                chip.isCloseIconVisible = true
                chip.setOnCloseIconClickListener {
                    val updatedCategoryList = statsViewModel.getCategories()?.toMutableList()?.apply {
                        remove(Category.valueOf(chip.text.toString()))
                    }
                    statsViewModel.setCategories(updatedCategoryList)
                    selectedCategoriesChipGroup.removeView(chip)
                }
                selectedCategoriesChipGroup.addView(chip)
            }
            // Show the ChipGroup
            selectedCategoriesChipGroup.visibility = View.VISIBLE
        }
        dialog.show()
    }
}