package com.example.testingapl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testingapl.databinding.ActivityHomeScreenBinding
import com.example.testingapl.databinding.ActivityUbahProfileBinding

class ubah_profile : AppCompatActivity() {


    private lateinit var bind : ActivityUbahProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityUbahProfileBinding.inflate(layoutInflater)
        setContentView(bind.root)


        bind.imageView13.setOnClickListener {
            onBackPressed()
        }
    }
}