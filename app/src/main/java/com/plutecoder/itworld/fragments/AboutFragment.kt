package com.plutecoder.itworld.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.plutecoder.itworld.R

class AboutFragment   : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_about, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load stored preferences
//        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
//        switchDarkMode.isChecked = sharedPref.getBoolean("isDarkMode", false)
//
//        // Set up spinner
//        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinnerLanguage.adapter = adapter
//
//        // Handle events
//        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
//            with(sharedPref.edit()) {
//                putBoolean("isDarkMode", isChecked)
//                apply()
//            }
//            updateTheme(isChecked)
//        }
    }

//    private fun updateTheme(isDarkMode: Boolean) {
//        val nightMode = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
//        AppCompatDelegate.setDefaultNightMode(nightMode)
//    }
}