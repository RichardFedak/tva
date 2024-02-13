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
import com.example.myapplication.viewmodels.SpendingsViewModel
import java.text.SimpleDateFormat
import java.util.Date

class Spendings: Fragment() {
    private lateinit var spendingsViewModel: SpendingsViewModel
    private lateinit var dateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        spendingsViewModel = ViewModelProvider(this)[SpendingsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_spendings, container, false)

        dateTextView = view.findViewById(R.id.dateTextView)

        // Retrieve selected date from arguments
        val selectedDate = arguments?.getString(ARG_SELECTED_DATE)

        // Display selected date in TextView
        dateTextView.text = selectedDate

        // Register on click event
        val detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        val addNewSpendingBtn: ImageButton = view.findViewById(R.id.addNewSpendingBtn)
        addNewSpendingBtn.setOnClickListener() {_ ->
            val created: Date = SimpleDateFormat("yyyy-MM-dd").parse(selectedDate)
            val newSpending = Expense(created = created)
            detailViewModel.setSelectedSpending(newSpending)
        }

        // Register adapter
        val spendingsList: ListView = view.findViewById(R.id.spendings_list)
        if (selectedDate != null) {
            val date =  SimpleDateFormat("yyyy-MM-dd").parse(selectedDate)
            if (date != null) {
                val spendings = spendingsViewModel.getExpenses(date)
                spendingsList.isClickable = true
                spendingsList.adapter = SpendingsListViewAdapter(requireContext(), ArrayList(spendings))
            }
        }

        return view
    }

    companion object {
        private const val ARG_SELECTED_DATE = "selected_date"

        @JvmStatic
        fun newInstance(selectedDate: String) =
            Spendings().apply {
                arguments = Bundle().apply {
                    putString(ARG_SELECTED_DATE, selectedDate)
                }
            }

        fun newInstance(selectedDate: Date) =
            Spendings().apply {
                arguments = Bundle().apply {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val formattedDate = dateFormat.format(selectedDate)
                    putString(ARG_SELECTED_DATE, formattedDate)
                }
            }
    }
}
