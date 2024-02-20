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
    private lateinit var dateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        expensesViewModel = ViewModelProvider(this)[ExpensesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expenses, container, false)

        dateTextView = view.findViewById(R.id.dateTextView)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Retrieve selected date from arguments
        val selectedDate = arguments?.getString(ARG_SELECTED_DATE)

        // Register on click event
        val detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        val addNewExpenseBtn: ImageButton = view.findViewById(R.id.addNewExpenseBtn)
        addNewExpenseBtn.setOnClickListener { _ ->
            val created = dateFormat.parse(selectedDate) //TODO check null
            if (created != null) {
                val newExpense = Expense(created = created)
                detailViewModel.setSelectedExpense(newExpense)
            }
        }

        // Register adapter
        val expenses: ListView = view.findViewById(R.id.expenses_list)
        if (selectedDate != null) {
            val date = dateFormat.parse(selectedDate)
            dateTextView.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date) //TODO check null
            if (date != null) {
                val expense = expensesViewModel.getExpenses(date)
                expenses.isClickable = true
                expenses.adapter = ExpensesListViewAdapter(requireContext(), ArrayList(expense))
            }
        }

        val date = dateFormat.parse(selectedDate)//TODO check null
        val expense = expensesViewModel.getExpenses(date)//TODO check null
        expenses.isClickable = true
        expenses.setOnItemClickListener { _, _, position, _ ->
            detailViewModel.setSelectedExpense(expense[position])
        }

        return view
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
