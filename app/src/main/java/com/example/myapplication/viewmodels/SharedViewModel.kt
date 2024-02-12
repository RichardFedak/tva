package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate


class SharedViewModel : ViewModel() {
    private val _selectedDate = MutableLiveData<LocalDate>()

    val selectedDate: LiveData<LocalDate> get() = _selectedDate

    fun setSelectedDate(year: Int, month: Int, day: Int) {
        _selectedDate.value = LocalDate.of(year, month, day)
    }
}