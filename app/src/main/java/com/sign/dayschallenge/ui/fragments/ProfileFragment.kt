package com.sign.dayschallenge.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sign.dayschallenge.R
import com.sign.dayschallenge.utils.Constants.Companion.PROF_NAME
import com.sign.dayschallenge.utils.Constants.Companion.PROF_SEX
import com.sign.dayschallenge.utils.Constants.Companion.SHARED_PREF
import kotlinx.android.synthetic.main.profile_fragment_layout.*

class ProfileFragment : Fragment(R.layout.profile_fragment_layout) {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

        initDropDownMenu()

        btn_confirm.setOnClickListener {
            saveProfileInfo()
            findNavController().navigate(R.id.action_profileFragment_to_mainFragment)
        }

    }

    private fun initDropDownMenu(){
        val items = listOf(getString(R.string.male), getString(R.string.female))
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (sex_select.editText as AutoCompleteTextView).setAdapter(adapter)
    }

    private fun saveProfileInfo(){
        val sharedPrefEditor = sharedPreferences.edit()
        if (profName.editText?.text.toString().isNotEmpty()){
            sharedPrefEditor.putString(PROF_NAME,profName.editText?.text.toString())
        }
        sharedPrefEditor.putString(PROF_SEX,sex_select.editText?.text.toString())
        sharedPrefEditor.apply()
        Log.e("TAGGER"," ${profName.editText?.text.toString()}    ${sex_select.editText?.text.toString()}   ")
    }
}