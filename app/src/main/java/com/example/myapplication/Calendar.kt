package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.viewmodels.SharedViewModel

class Calendar : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        calendarView.setOnDateChangeListener { _, year, month, day ->
            sharedViewModel.setSelectedDate(year, month + 1, day)
        }

        return view
    }

}