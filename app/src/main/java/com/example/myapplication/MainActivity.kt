package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.myapplication.data.Category
import com.example.myapplication.data.Expense
import com.example.myapplication.data.ExpenseDatabase
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewmodels.SharedViewModel
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Calendar())
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]


        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_nav_calendar -> replaceFragment(Calendar())
                R.id.bottom_nav_stats -> replaceFragment(Stats())
                else -> {}
            }
            true
        }

        sharedViewModel.selectedDate.observe(this, Observer { date ->
            // Handle the selected date change
            val spendingsFragment = Spendings.newInstance(date.toString())
            replaceFragment(spendingsFragment)
        })

        val db = Room.databaseBuilder<ExpenseDatabase>(
            applicationContext,
            ExpenseDatabase::class.java, "database-name"
        ).build()
        lifecycleScope.launch {
            db.expenseDao().addExpense(Expense(1,20.2,"test", Date(), Category.Food))
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}