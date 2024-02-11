package com.example.myapplication

import SpendingDetail
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewmodels.SharedViewModel

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
            // TODO SHOULD BE
            // val spendingsFragment = Spendings.newInstance(date.toString()) // TODO CALL THIS
            val spendingsFragment = SpendingDetail.newInstance(date.toString()) // TODO REMOVE THIS LINE - <ONLY TESTING NOW>
            replaceFragment(spendingsFragment)
        })

//        val db = Room.databaseBuilder<ExpenseDatabase>(
//            applicationContext,
//            ExpenseDatabase::class.java, "spendings_db"
//        ).build()
//        lifecycleScope.launch {
//            db.expenseDao().addExpense(Expense(1,20.2,"test", Date(), Category.Food))
//        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}