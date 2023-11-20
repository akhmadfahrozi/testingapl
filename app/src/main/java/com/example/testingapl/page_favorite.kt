package com.example.testingapl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testingapl.databinding.ActivityDashboardMuridBinding
import com.example.testingapl.databinding.ActivityPageFavoriteBinding

class page_favorite : Fragment() {
    private lateinit var bind: ActivityPageFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.activity_main, null);
        bind = ActivityPageFavoriteBinding.inflate(inflater, container, false)
        bind.getRoot()
        return bind.root
    }
}