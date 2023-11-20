package com.example.testingapl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.testingapl.database.User
import com.example.testingapl.databinding.ActivityLoginGuruBinding
import com.example.testingapl.databinding.ActivityLoginMuridBinding
import com.example.testingapl.utils.prefrence
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class login_murid : AppCompatActivity() {

    lateinit var usernme: String
    lateinit var psword: String
    lateinit var simpan: prefrence
    lateinit var mDatabase: DatabaseReference


    private lateinit var bind: ActivityLoginMuridBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginMuridBinding.inflate(layoutInflater)
        setContentView(bind.root)
        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        simpan = prefrence(this)

        if (simpan.getvalues("status").equals("1"))
        {
            finishAffinity()
            var jo = Intent(this@login_murid, MainActivity::class.java)
            startActivity(jo)
        }
        bind.textView5.setOnClickListener {
            val i = Intent(this,daftar_akun::class.java)
            startActivity(i)
        }
        bind.but2.setOnClickListener {
            usernme = bind.emll.text.toString()
            psword = bind.pss.text.toString()
            if (usernme.equals("")) {
                bind.eml.error = "Masukan Username Anda"
                bind.eml.requestFocus()
            } else if (psword.equals("")) {
                bind.pss.error = "Password Salah"
                bind.pss.requestFocus()
            } else {
                PushLogin(usernme, psword)
            }
        }

    }
    private fun PushLogin(usernme: String, psword: String) {

        mDatabase.child(usernme).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@login_murid,
                    databaseError.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(
                        this@login_murid,
                        "Username Tidak Ditemukan",
                        Toast.LENGTH_LONG
                    ).show()
                } else {

                    if (user.password.equals(psword)) {

                        simpan.setvalues("nama",user.username.toString())
                        simpan.setvalues("email",user.email.toString())
                        simpan.setvalues("status","1")

                        finishAffinity()
                        var intent = Intent(this@login_murid, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@login_murid,
                            "Password Anda Salah",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }
}