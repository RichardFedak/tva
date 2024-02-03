package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class Spendings: Fragment() {

    private lateinit var dateTextView: TextView

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
    }
}
