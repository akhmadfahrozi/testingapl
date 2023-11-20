package com.example.testingapl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.testingapl.database.User
import com.example.testingapl.databinding.ActivityDaftarAkunBinding
import com.example.testingapl.databinding.ActivityHomeScreenBinding
import com.example.testingapl.utils.prefrence
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class daftar_akun : AppCompatActivity() {

    lateinit var sUsername: String
    lateinit var sPassword: String
    lateinit var sNama: String
    lateinit var sEmail: String

    lateinit var mfirebase: FirebaseDatabase
    lateinit var mfirebasedata: DatabaseReference
    lateinit var mDatabaseReference: DatabaseReference

    private lateinit var prefernce: prefrence

    private lateinit var bind : ActivityDaftarAkunBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDaftarAkunBinding.inflate(layoutInflater)
        setContentView(bind.root)
        prefernce = prefrence(this)

        mfirebase = FirebaseDatabase.getInstance()
        mfirebasedata = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = mfirebase.getReference("User")

        bind.but2.setOnClickListener {
            sUsername = bind.nama.text.toString()
            sPassword = bind.passs.text.toString()
            sEmail = bind.eml.text.toString()

            if (sUsername.equals("")) {
                bind.nama.error = "silahkan masukkan username anda"
                bind.nama.requestFocus()
            } else if (sPassword.equals("")) {
                bind.passs.error = "silahkan masukkan password anda"
                bind.passs.requestFocus()
            } else if (sEmail.equals("")) {
                bind.eml.error = "silahkan masukkan nama anda"
                bind.eml.requestFocus()
            } else {
                saveusername(sUsername, sPassword , sEmail)
            }

        }
    }

    private fun saveusername(sUsername: String, sPassword: String, sEmail: String) {
        var user = User()
        user.username = sUsername
        user.email = sEmail
        user.password = sPassword

        //bisa pilih dari salah satu yg mau di gantikan sUsername di user

        if (sUsername != null) {
            checkdata(sUsername, user)
        }
    }

    private fun checkdata(sUsername: String, data: User) {
        mDatabaseReference.child(sUsername).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(datbaseError: DatabaseError) {
                Toast.makeText(this@daftar_akun, ""+datbaseError.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user ==null){
                    mDatabaseReference.child(sUsername).setValue(data)


                    prefernce.setvalues("user", data.username.toString())
                    prefernce.setvalues("email", data.email.toString())
                    prefernce.setvalues("status", "1")

                    var goto= Intent(this@daftar_akun,
                        MainActivity::class.java)
                        .putExtra("data",data)
                    startActivity(goto)


                }else{
                    Toast.makeText(this@daftar_akun, "Berhasil daftar akun", Toast.LENGTH_LONG).show()
                }

            }


        })

    }

    }
