package com.example.santepriceindex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.santepriceindex.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_price_watch -> {
                    loadFragment(PriceWatchFragment())
                    true
                }
                R.id.navigation_profit_calc -> {
                    loadFragment(ProfitCalcFragment())
                    true
                }
                R.id.navigation_price_board -> {
                    loadFragment(PriceBoardFragment())
                    true
                }
                R.id.navigation_trends -> {
                    loadFragment(TrendsFragment())
                    true
                }
                else -> false
            }
        }

        // Load default fragment
        if (savedInstanceState == null) {
            navView.selectedItemId = R.id.navigation_price_watch
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, fragment)
            .commit()
    }
}