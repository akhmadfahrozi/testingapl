package com.example.testingapl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testingapl.databinding.ActivityHomeScreenBinding
import com.example.testingapl.databinding.ActivityLoginGuruBinding

class login_guru : AppCompatActivity() {
    private lateinit var bind: ActivityLoginGuruBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginGuruBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.textView5.setOnClickListener {
            val i = Intent(this, daftar_akun::class.java)
            startActivity(i)
        }
        bind.but2.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }
}