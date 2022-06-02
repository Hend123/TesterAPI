package com.hend.testingbackendapis

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hend.testingbackendapis.databinding.ActivityMainBinding
import com.hend.testingbackendapis.representation.views.history.HistoryFragment
import com.hend.testingbackendapis.representation.views.testapifrag.TestAPIFragment

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var testAPIFragment: TestAPIFragment
    private lateinit var historyFragment: HistoryFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()

        loadFragment(testAPIFragment)
        bottomNavSettings()

    }

    private fun init() {
        testAPIFragment = TestAPIFragment()
        historyFragment = HistoryFragment()
    }

    private fun bottomNavSettings() {
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(testAPIFragment)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    loadFragment(historyFragment)
                    return@setOnItemSelectedListener true
                }
            }
        }
    }

    fun setBottomNavigationVisibility(visibility: Int) {
        // get the reference of the bottomNavigationView and set the visibility.
        binding.bottomNav.visibility = visibility
    }


    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.root_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}