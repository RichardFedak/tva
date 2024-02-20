package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewmodels.DetailViewModel
import com.example.myapplication.viewmodels.SharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        replaceFragment(Calendar())
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_nav_calendar -> replaceFragment(Calendar())
                R.id.bottom_nav_stats -> replaceFragment(Stats())
                else -> {}
            }
            true
        }

        sharedViewModel.selectedDate.observe(this, Observer { date -> //TODO
            val expensesFragment = Expenses.newInstance(date.toString())
            replaceFragment(expensesFragment)
            uncheckAllNavItems(bottomNav)
        })

        detailViewModel.selectedExpense.observe(this, Observer { expense -> // TODO
            if (expense != null) {
                replaceFragment(ExpenseDetail())
            } else {
                val expensesFragment = Expenses.newInstance(detailViewModel.lastDate)
                replaceFragment(expensesFragment)
            }
        })
    }

    private fun uncheckAllNavItems(bottomNav: BottomNavigationView) {
        bottomNav.menu.setGroupCheckable(0, true, false)
        bottomNav.menu.forEach { it.isChecked = false }
        bottomNav.menu.setGroupCheckable(0, true, true)
    }

    private fun replaceFragment(fragment: Fragment){
        if (!::sharedViewModel.isInitialized) return
        if (sharedViewModel.activeFragment.value?.javaClass == fragment.javaClass) return
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        sharedViewModel.setActiveFragment(fragment)
        fragmentTransaction.commit()
    }


}