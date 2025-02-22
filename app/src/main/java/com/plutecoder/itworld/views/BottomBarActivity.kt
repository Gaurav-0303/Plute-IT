package com.plutecoder.itworld.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.plutecoder.itworld.R
import com.plutecoder.itworld.fragments.AboutFragment
import com.plutecoder.itworld.fragments.ConnectFragment
import com.plutecoder.itworld.fragments.HomeFragment
import com.plutecoder.itworld.fragments.PlutecoderFragment

class BottomBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottombar_screen)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_SECURE,
//            WindowManager.LayoutParams.FLAG_SECURE
//        )
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

    private fun attachFragment(fragment : Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, "")
            .commit()

    }

}