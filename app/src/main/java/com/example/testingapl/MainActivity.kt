package com.example.testingapl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.testingapl.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var fm: FragmentManager
    lateinit var ft: FragmentTransaction
    lateinit var ool: BottomNavigationView

    private lateinit var bind: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ool = findViewById(R.id.bottomNavigationView)


        fm = supportFragmentManager
        ft = fm.beginTransaction()
        ft.replace(R.id.framee, dashboard_murid()).commit()

        ool.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    private fun setfragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmenttransisi = fragmentManager.beginTransaction()
        fragmenttransisi.replace(R.id.framee, fragment)
        fragmenttransisi.commit()
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_1 -> {
                    fm = supportFragmentManager
                    ft = fm.beginTransaction()
                    ft.replace(R.id.framee, dashboard_murid()).commit()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.page_2
                -> {
                    fm = supportFragmentManager
                    ft = fm.beginTransaction()
                    ft.replace(R.id.framee, page_favorite()).commit()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.page_3 -> {
                    fm = supportFragmentManager
                    ft = fm.beginTransaction()
                    ft.replace(R.id.framee, page_profile()).commit()
                    return@OnNavigationItemSelectedListener true
                }
            }

            false
        }
}
