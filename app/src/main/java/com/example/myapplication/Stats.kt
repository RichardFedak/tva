package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TableRow.LayoutParams
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.Category
import com.example.myapplication.viewmodels.StatsViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


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
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

        val statsDateFromFilter = view.findViewById<TextView>(R.id.statsDateFromFilter)
        val statsDateToFilter = view.findViewById<TextView>(R.id.statsDateToFilter)
        val statsButtonFilter = view.findViewById<Button>(R.id.statsButtonFilter)
        val statsButtonReset = view.findViewById<Button>(R.id.statsResetButton)
        val selectCategoriesButton = view.findViewById<Button>(R.id.selectCategoriesButton)
        val selectedCategoriesChipGroup = view.findViewById<ChipGroup>(R.id.selectedCategoriesChipGroup)
        selectCategoriesButton.setOnClickListener {
            showCategorySelectionDialog(selectedCategoriesChipGroup, context)
        }

        statsDateFromFilter.setOnClickListener {
            showDatePicker(statsDateFromFilter, isFromDate = true, context)
        }

        statsDateToFilter.setOnClickListener {
            showDatePicker(statsDateToFilter, isFromDate = false, context)
        }

        statsButtonFilter.setOnClickListener{
            statsViewModel.getExpenses().observe(viewLifecycleOwner) { expenses ->
                removeResultRows(view)
                var totalExpenses = 0.0
                var totalExpensesCount = 0
                if (expenses.isEmpty()){
                    addInfoRow(getString(R.string.no_results))
                }
                else{
                    for (ex in expenses) {
                        addExpenseRow(ex.category.getName(context), String.format("%.2f", ex.totalExpense), ex.expensesCount)
                        totalExpenses += ex.totalExpense
                        totalExpensesCount += ex.expensesCount
                    }
                    addExpenseRow(getString(R.string.stats_total), String.format("%.2f", totalExpenses), totalExpensesCount, isTotal = true)
                }
            }
        }

        statsButtonReset.setOnClickListener {
            statsViewModel.resetFilter()
            removeResultRows(view)
            statsDateFromFilter.text = getString(R.string.stats_filter_from)
            statsDateToFilter.text = getString(R.string.stats_filter_to)
            selectedCategoriesChipGroup.removeAllViews()
            selectedCategoriesChipGroup.visibility = View.GONE
        }
    }

    private fun showDatePicker(textView: TextView, isFromDate: Boolean, context: Context) {
        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH)
        val dayOfMonth = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = java.util.Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDayOfMonth)
                }.time

                val dateString = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                textView.text = dateString

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

    private fun showCategorySelectionDialog(selectedCategoriesChipGroup : ChipGroup, context: Context) {
        val categories = Category.entries.map { it.getName(context) }.toTypedArray()
        val alreadySelectedCategories = statsViewModel.getCategories()?.map { it.getName(context) } ?: emptyList()
        val dialog = CategorySelectionDialog(context, categories, alreadySelectedCategories)

        dialog.setOnConfirmClickListener { selectedCategories ->
            val selectedCategoriesList = selectedCategories.map { Category.getValue(it, context) }
            statsViewModel.setCategories(selectedCategoriesList)

            selectedCategoriesChipGroup.removeAllViews()
            // Add chips for selected categories
            for (category in selectedCategories) {
                val chip = Chip(context)
                chip.text = category
                chip.isCloseIconVisible = true
                chip.gravity = Gravity.CENTER
                chip.setOnCloseIconClickListener {
                    val updatedCategoryList = statsViewModel.getCategories()?.toMutableList()?.apply {
                        remove(Category.getValue(chip.text.toString(), context))
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

    private fun addInfoRow(message: String){
        val tableLayout = view?.findViewById<TableLayout>(R.id.stats_table)

        val row = TableRow(context)

        row.addView(createViewForResult(message))

        tableLayout?.addView(row)
    }

    private fun addExpenseRow(category: String, expense: String, count: Int, isTotal: Boolean = false) {
        val tableLayout = view?.findViewById<TableLayout>(R.id.stats_table)

        val row = TableRow(context)

        row.addView(createViewForResult(category, isTotal))
        row.addView(createViewForResult(expense, isTotal))
        row.addView(createViewForResult(count.toString(), isTotal))

        tableLayout?.addView(row)
    }

    private fun createViewForResult(value: String, isTotal: Boolean = false) : View{
        val params = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
        val view = TextView(context)
        view.layoutParams = params
        view.text = value
        view.gravity = Gravity.CENTER
        view.setPadding(15, 0, 0, 0)
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        if (isTotal) {
            view.setTypeface(null, Typeface.BOLD)
        }
        return view
    }

    private fun removeResultRows(view: View){
        val tableLayout = view.findViewById<TableLayout>(R.id.stats_table)
        val count = (tableLayout?.childCount ?: 0) - 1 // -1 for header row
        for (i in 1..count){
            tableLayout?.removeViewAt(1)
        }
    }
}