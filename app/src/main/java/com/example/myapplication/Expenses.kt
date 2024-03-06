package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.Expense
import com.example.myapplication.viewmodels.DetailViewModel
import com.example.myapplication.viewmodels.ExpensesViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Expenses: Fragment() {
    private lateinit var expensesViewModel: ExpensesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        expensesViewModel = ViewModelProvider(this)[ExpensesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expenses, container, false)
        val detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)
        val expensesListView: ListView = view.findViewById(R.id.expenses_list)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Retrieve selected date from arguments
        val selectedDate = arguments?.getString(ARG_SELECTED_DATE) ?: return view

        initAddNewExpenseButton(view, dateFormat, selectedDate, detailViewModel)

        val date = dateFormat.parse(selectedDate)
        if (date != null) {
            dateTextView.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)

            initExpensesListView(date, expensesListView, detailViewModel)
        }

        return view
    }

    private fun initExpensesListView(
        date: Date,
        expensesListView: ListView,
        detailViewModel: DetailViewModel
    ) {
        expensesViewModel.getExpenses(date).observe(viewLifecycleOwner) { expenses ->
            expensesListView.isClickable = true
            expensesListView.adapter = ExpensesListViewAdapter(requireContext(), ArrayList(expenses))
            expensesListView.setOnItemClickListener { _, _, position, _ ->
                val expenseId = expenses[position].id
                detailViewModel.getExpense(expenseId).observe(viewLifecycleOwner) {
                    detailViewModel.setSelectedExpense(it)
                }
            }
        }
    }

    private fun initAddNewExpenseButton(
        view: View,
        dateFormat: SimpleDateFormat,
        selectedDate: String,
        detailViewModel: DetailViewModel
    ) {
        val addNewExpenseBtn: ImageButton = view.findViewById(R.id.addNewExpenseBtn)

        addNewExpenseBtn.setOnClickListener { _ ->
            val created = dateFormat.parse(selectedDate)
            if (created != null) {
                val newExpense = Expense(created = created)
                detailViewModel.setSelectedExpense(newExpense)
            }
        }
    }

    companion object {
        private const val ARG_SELECTED_DATE = "selected_date"

        @JvmStatic
        fun newInstance(selectedDate: String) =
            Expenses().apply {
                arguments = Bundle().apply {
                    putString(ARG_SELECTED_DATE, selectedDate)
                }
            }

        fun newInstance(selectedDate: Date) =
            Expenses().apply {
                arguments = Bundle().apply {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate)
                    putString(ARG_SELECTED_DATE, formattedDate)
                }
            }
    }
}
