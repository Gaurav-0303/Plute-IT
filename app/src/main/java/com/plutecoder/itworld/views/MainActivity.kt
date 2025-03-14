package com.plutecoder.itworld.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.plutecoder.itworld.R
import com.plutecoder.itworld.databinding.ActivityMainBinding
import com.plutecoder.itworld.fragments.AboutFragment
import com.plutecoder.itworld.fragments.ConnectFragment
import com.plutecoder.itworld.fragments.HomeFragment
import com.plutecoder.itworld.fragments.PlutecoderFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isDarkModeEnabled(this)) {
            binding.bottom.setShadowColorLight(ContextCompat.getColor(this, R.color.neumorph_shadow_light))
            binding.bottom.setShadowColorDark(ContextCompat.getColor(this, R.color.neumorph_shadow_dark))
        }

        binding.bottomBar.setOnItemSelectedListener { it: Int ->
            when(it){
                0 -> navigateFragment(HomeFragment(), "", "", "Home")
                1 -> navigateFragment(PlutecoderFragment(), "", "", "Plutecoder")
                2 -> navigateFragment(AboutFragment(), "", "", "About")
                3 -> navigateFragment(ConnectFragment(), "", "", "Connect")
            }

        }
        navigateFragment(HomeFragment(), "", "", "Home")
    }

    //it will load fragment into container
    private fun navigateFragment(fragment: Fragment, key: String, value: String, title: String) {
        val bundle = Bundle().apply {
            putString(key, value)
        }
        fragment.arguments = bundle
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        runOnUiThread {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }



}