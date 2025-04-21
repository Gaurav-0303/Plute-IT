package com.plutecoder.itworld.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.plutecoder.itworld.R
import com.plutecoder.itworld.databinding.ActivityMainBinding
import com.plutecoder.itworld.fragments.AboutFragment
import com.plutecoder.itworld.fragments.ConnectFragment
import com.plutecoder.itworld.fragments.HomeFragment
import com.plutecoder.itworld.fragments.PlutecoderFragment
import com.plutecoder.itworld.viewModels.CategoryViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var activeFragment: Fragment? = null
    private val LAST_SELECTED_FRAGMENT = null

    companion object {
        var categoryViewModel : CategoryViewModel? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val itemid = intent.getStringExtra("itemId")
        if(!itemid.isNullOrEmpty()){
            val intent = Intent(this, RoadmapActivity::class.java).apply {
                putExtra("itemId", itemid) // Pass the item uid to RoadmapActivity
            }
            startActivity(intent)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isDarkModeEnabled(this)) {
            binding.bottom.setShadowColorLight(ContextCompat.getColor(this, R.color.neumorph_shadow_light))
            binding.bottom.setShadowColorDark(ContextCompat.getColor(this, R.color.neumorph_shadow_dark))
        }

        //initialize category view model
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        // Restore the last selected fragment after recreation
        val lastFragmentTag = savedInstanceState?.getString(LAST_SELECTED_FRAGMENT) ?: "Home"
        val lastFragment = supportFragmentManager.findFragmentByTag(lastFragmentTag)

        if (lastFragment != null) {
            activeFragment = lastFragment
        } else {
            navigateFragment(HomeFragment(), "", "", "Home")
        }

        binding.bottomBar.itemActiveIndex = when (lastFragmentTag) {
            "Home" -> 0
            "Plutecoder" -> 1
            "About" -> 2
            "Connect" -> 3
            else -> 0
        }

        binding.bottomBar.setOnItemSelectedListener { it: Int ->
            when (it) {
                0 -> navigateFragment(HomeFragment(), "", "", "Home")
                1 -> navigateFragment(PlutecoderFragment(), "", "", "Plutecoder")
                2 -> navigateFragment(AboutFragment(), "", "", "About")
                3 -> navigateFragment(ConnectFragment(), "", "", "Connect")
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val itemid = intent.getStringExtra("itemId")
        if(!itemid.isNullOrEmpty()){
            val intent = Intent(this, RoadmapActivity::class.java).apply {
                putExtra("itemId", itemid) // Pass the item uid to RoadmapActivity
            }
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SELECTED_FRAGMENT, activeFragment?.tag)
    }

    //it will load fragment into container
    private fun navigateFragment(fragment: Fragment, key: String, value: String, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTag = title  // Use title as a unique tag for the fragment

        val existingFragment = fragmentManager.findFragmentByTag(fragmentTag)

        fragmentManager.beginTransaction().apply {

            activeFragment?.let { hide(it) }  // Hide active fragment if it's not null

            if (existingFragment != null) {
                show(existingFragment)  // Show the existing fragment
                activeFragment = existingFragment
            } else {
                val bundle = Bundle().apply { putString(key, value) }
                fragment.arguments = bundle

                add(R.id.container, fragment, fragmentTag) // Add new fragment
                activeFragment = fragment
            }
            commit()
        }
    }
}