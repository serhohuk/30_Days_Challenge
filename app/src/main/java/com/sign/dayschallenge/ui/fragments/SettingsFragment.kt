package com.sign.dayschallenge.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.sign.dayschallenge.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
    }

}