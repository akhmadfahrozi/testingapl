package com.example.testingapl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testingapl.databinding.ActivityHomeScreenBinding
import com.example.testingapl.utils.prefrence

class home_screen : AppCompatActivity() {

    lateinit var simpan : prefrence
    private lateinit var bind : ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(bind.root)

        simpan = prefrence(this)
        if (simpan.getvalues("status").equals("1")) {
            finishAffinity()
            var goo = Intent(this@home_screen, login_murid::class.java)
            startActivity(goo)
        }
        bind.muridd.setOnClickListener {
            val i = Intent(this,login_murid::class.java)
            startActivity(i)
        }
        bind.guruu.setOnClickListener {
            val i = Intent(this,login_guru::class.java)
            startActivity(i)
        }
    }
}