package com.example.testingapl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testingapl.databinding.ActivityDashboardMuridBinding
import com.example.testingapl.utils.prefrence

class dashboard_murid : Fragment() {
    private lateinit var bind: ActivityDashboardMuridBinding

    private lateinit var preference: prefrence

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.activity_main, null);
        bind = ActivityDashboardMuridBinding.inflate(inflater, container, false)
        bind.getRoot()


        bind.cardView.setOnClickListener {
            val i = Intent(context, detail::class.java)
            startActivity(i)
        }



        return bind.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preference = prefrence(requireActivity()!!.applicationContext)

    }
}