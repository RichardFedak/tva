package com.example.myapplication.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate


class SharedViewModel : ViewModel() {
    private val _selectedDate = MutableLiveData<LocalDate>()
    private val _activeFragment = MutableLiveData<Fragment>()

    val selectedDate: LiveData<LocalDate> get() = _selectedDate
    val activeFragment: LiveData<Fragment> get() = _activeFragment


    fun setSelectedDate(year: Int, month: Int, day: Int) {
        _selectedDate.value = LocalDate.of(year, month, day)
    }
    fun setActiveFragment(fragment: Fragment) {
        _activeFragment.value = fragment
    }
}