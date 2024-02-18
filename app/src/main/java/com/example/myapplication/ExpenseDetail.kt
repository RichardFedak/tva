package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.Category
import com.example.myapplication.data.Expense
import com.example.myapplication.viewmodels.DetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class ExpenseDetail : Fragment() {
    private lateinit var dateTextView: TextView
    private lateinit var expenseValueEditText: EditText
    private lateinit var noteEditText: EditText
    private lateinit var categoryTextView: TextView
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense_detail, container, false)

        detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        initBackButton(view)

        dateTextView = view.findViewById(R.id.dateTextView)
        expenseValueEditText = view.findViewById(R.id.expenseEditText)
        noteEditText = view.findViewById(R.id.noteEditText)
        categoryTextView = view.findViewById(R.id.categoryTextView)

        val expense = detailViewModel.selectedExpense.value
        if (expense != null) {
            initDeleteButton(view, expense.id == 0)

            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            dateTextView.text = dateFormat.format(expense.created)
            expenseValueEditText.setText(expense.value.toString())
            noteEditText.setText(expense.note)
            categoryTextView.text = expense.category.toString()
        }

        categoryTextView.setOnClickListener {
            showCategoryDialog()
        }

        initSaveButton(view)

        return view
    }

    private fun initSaveButton(view: View) {
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val date = dateFormat.parse(dateTextView.text.toString())!!
            val expenseValueString = expenseValueEditText.text.toString()
            val note = noteEditText.text.toString()
            val category = Category.valueOf(categoryTextView.text.toString())

            if (!isExpenseValueValid(expenseValueString)) {
                return@setOnClickListener
            }
            val expenseValue = expenseValueString.toDouble()

            // It is OK to take id from selectedExpense because it cannot be changed by the user
            val id = detailViewModel.selectedExpense.value!!.id
            val newExpense = Expense(id, expenseValue, note, date, category)
            detailViewModel.saveExpense(newExpense)

            showToast(getString(R.string.toast_expense_created))

            navigateBackToExpenses()
        }
    }

    private fun isExpenseValueValid(expenseValueString: String): Boolean {
        if (expenseValueString.isBlank()) {
            showToast(getString(R.string.toast_expense_value_can_not_be_empty))
            return false
        }

        val expenseValue = try {
            expenseValueString.toDouble()
        } catch (e: NumberFormatException) {
            showToast(getString(R.string.toast_invalid_number))
            return false
        }

        if (expenseValue <= 0) {
            showToast(getString(R.string.toast_expense_value_must_be_greater_than_zero))
            return false
        }

        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun navigateBackToExpenses() {
        detailViewModel.setSelectedExpense(null)
    }

    private fun initDeleteButton(view: View, shouldHideDeleteButton: Boolean) {
        val deleteButton = view.findViewById<Button>(R.id.deleteButton)

        if (shouldHideDeleteButton) {
            deleteButton.isVisible = false
        } else {
            deleteButton.setOnClickListener {
                detailViewModel.deleteExpense()
                navigateBackToExpenses()
            }
        }
    }

    private fun createBackAlertDialog(): AlertDialog {
        val builder = AlertDialog.Builder(requireContext())

        builder.setMessage(getString(R.string.discard_changes_dialog_msg))
            .setPositiveButton(getString(R.string.discard_changes_dialog_discard)) { _, _ ->
                navigateBackToExpenses()
            }
            .setNegativeButton(getString(R.string.discard_changes_dialog_no)) { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }

    private fun hasInputsChanged(): Boolean {
        val expense = detailViewModel.selectedExpense.value ?: return false

        if (expenseValueEditText.text.toString() != expense.value.toString()) {
            return true
        }

        if (noteEditText.text.toString() != expense.note) {
            return true
        }

        return categoryTextView.text.toString() != expense.category.toString()
    }

    private fun initBackButton(view: View) {
        val backAlertDialog = createBackAlertDialog()

        val backButton = view.findViewById<ImageButton>(R.id.backButton)

        backButton.setOnClickListener {
            if (hasInputsChanged()) {
                backAlertDialog.show()
            } else {
                navigateBackToExpenses()
            }
        }
    }

    private fun showCategoryDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.category_dialog_layout, null)

        val categoryListView = view.findViewById<ListView>(R.id.categoryListView)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            Category.entries.toTypedArray()
        )
        categoryListView.adapter = adapter

        val dialog = builder.setView(view)
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }.create()

        categoryListView.setOnItemClickListener { parent, _, position, _ ->
            val selectedCategory = parent.getItemAtPosition(position) as Category
            categoryTextView.text = selectedCategory.toString()
            dialog.dismiss()
        }

        dialog.show()
    }
}