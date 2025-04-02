package com.plutecoder.itworld.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.plutecoder.itworld.R
import com.plutecoder.itworld.databinding.FragmentConnectBinding
import com.plutecoder.itworld.views.isDarkModeEnabled
import soup.neumorphism.NeumorphCardView

class ConnectFragment : Fragment() {

    private lateinit var binding: FragmentConnectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConnectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchDarkMode.isChecked = isDarkModeEnabled(requireContext())

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        callDarkMode()
    }

    private fun callDarkMode() {
        darkModeChanges(binding.profileCard)
        darkModeChanges(binding.accountCard)
        darkModeChanges(binding.nameCard)
        darkModeChanges(binding.emailCard)
        darkModeChanges(binding.notificationCard)
        darkModeChanges(binding.soundCard)
        darkModeChanges(binding.badgeCard)
        darkModeChanges(binding.lockScreenCard)
        darkModeChanges(binding.preferencesCard)
        darkModeChanges(binding.languageCard)
        darkModeChanges(binding.darkModeCard)
        darkModeChanges(binding.textSizeCard)
    }

    private fun darkModeChanges(card: NeumorphCardView) {
        if (isDarkModeEnabled(requireContext())) {
            card.setShadowColorLight(ContextCompat.getColor(requireContext(), R.color.neumorph_shadow_light))
            card.setShadowColorDark(ContextCompat.getColor(requireContext(), R.color.neumorph_shadow_dark))
        }
    }
}
