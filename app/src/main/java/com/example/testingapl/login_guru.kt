package com.example.testingapl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.testingapl.database.User
import com.example.testingapl.databinding.ActivityHomeScreenBinding
import com.example.testingapl.databinding.ActivityLoginGuruBinding
import com.example.testingapl.utils.prefrence
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class login_guru : AppCompatActivity() {
    private lateinit var bind: ActivityLoginGuruBinding
    lateinit var psword: String
    lateinit var eml: String
    lateinit var simpan: prefrence
    lateinit var mDatabase: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginGuruBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.textView5.setOnClickListener {
            val i = Intent(this, daftar_akun::class.java)
            startActivity(i)
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        simpan = prefrence(this)

        if (simpan.getvalues("status").equals("1"))
        {
            finishAffinity()
            var jo = Intent(this@login_guru, MainActivity_guru::class.java)
            startActivity(jo)
        }
        bind.textView5.setOnClickListener {
            val i = Intent(this,daftar_akun::class.java)
            startActivity(i)
        }
        bind.but2.setOnClickListener {
             eml = bind.emll.text.toString()
            psword = bind.pss.text.toString()
            if (eml.equals("")) {
                bind.eml.error = "Masukan Username Anda"
                bind.eml.requestFocus()
            } else if (psword.equals("")) {
                bind.pss.error = "Password Salah"
                bind.pss.requestFocus()
            } else {
                PushLogin(eml, psword)
            }
        }

    }
    private fun PushLogin(eml: String, psword: String) {

        mDatabase.child(eml).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@login_guru,
                    databaseError.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(
                        this@login_guru,
                        "Username Tidak Ditemukan",
                        Toast.LENGTH_LONG
                    ).show()
                } else {

                    if (user.password.equals(psword)) {

                        simpan.setvalues("nama",user.username.toString())
                        simpan.setvalues("email",user.email.toString())
                        simpan.setvalues("status","1")

                        finishAffinity()
                        var intent = Intent(this@login_guru, MainActivity_guru::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@login_guru,
                            "Password Anda Salah",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }
//        bind.but2.setOnClickListener {
//            val i = Intent(this, MainActivity::class.java)
//            startActivity(i)
//        }
    }
