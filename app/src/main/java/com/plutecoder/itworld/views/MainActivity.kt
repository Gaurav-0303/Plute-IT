package com.plutecoder.itworld.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_SECURE,
//            WindowManager.LayoutParams.FLAG_SECURE
//        )

        if (this.isDarkModeEnabled(this)) {
            binding.bottom.setShadowColorLight(ContextCompat.getColor(this, R.color.neumorph_shadow_light))
            binding.bottom.setShadowColorDark(ContextCompat.getColor(this, R.color.neumorph_shadow_dark))
        }

        findViewById<me.ibrahimsn.lib.SmoothBottomBar>(R.id.bottomBar).setOnItemSelectedListener { it: Int ->
            when(it){

               0 -> {
                    attachFragment(HomeFragment())
                }
               1 ->{
                    attachFragment(PlutecoderFragment())
                }
               2 ->{
                    attachFragment(AboutFragment())
                }
               3 ->{
                   attachFragment(ConnectFragment())
               }
            }

        }


        attachFragment(HomeFragment())
    }
//
    private fun attachFragment(fragment : Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, "")
            .commit()

    }

}