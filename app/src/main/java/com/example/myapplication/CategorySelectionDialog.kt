package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class CategorySelectionDialog(
        context: Context,
        private val categories: Array<String>,
        private val selectedCategories: List<String>
    ) : Dialog(context) {
    private lateinit var categoryListView: ListView
    private lateinit var confirmButton: Button
    private lateinit var selectedCategoriesSet: MutableSet<String>

    private var onConfirmClickListener: ((selectedCategories: List<String>) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_category_selection)

        categoryListView = findViewById(R.id.categoryListView)
        confirmButton = findViewById(R.id.confirmButton)

        selectedCategoriesSet = selectedCategories.toMutableSet()

        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_multiple_choice, categories)
        categoryListView.adapter = adapter
        categoryListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        for (i in categories.indices) {
            if (selectedCategoriesSet.contains(categories[i])) {
                categoryListView.setItemChecked(i, true)
            }
        }

        confirmButton.setOnClickListener {
            selectedCategoriesSet.clear()
            val checkedItemPositions = categoryListView.checkedItemPositions
            for (i in 0 until checkedItemPositions.size()) {
                val key = checkedItemPositions.keyAt(i)
                if (checkedItemPositions[key]) {
                    selectedCategoriesSet.add(categories[key])
                }
            }
            onConfirmClickListener?.invoke(selectedCategoriesSet.toList())
            dismiss()
        }
    }

    fun setOnConfirmClickListener(listener: (selectedCategories: List<String>) -> Unit) {
        onConfirmClickListener = listener
    }
}
