package com.example.aviatickets.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.aviatickets.databinding.ActivityMainBinding
import com.example.aviatickets.fragment.OfferListFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment = OfferListFragment.newInstance() // Assuming you have a newInstance() method in ExercisesFragment
        fragmentTransaction.replace(com.example.aviatickets.R.id.fragment_container_view, fragment)
        fragmentTransaction.commit()
    }


}
