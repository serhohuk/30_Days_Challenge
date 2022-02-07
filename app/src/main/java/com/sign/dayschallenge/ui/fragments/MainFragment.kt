package com.sign.dayschallenge.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.text.Layout
import android.util.DisplayMetrics
import android.view.View
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sign.dayschallenge.R
import com.sign.dayschallenge.adapters.ChallengeAdapter
import com.sign.dayschallenge.application.MyApplication
import com.sign.dayschallenge.data.Challenge
import com.sign.dayschallenge.ui.MainActivity
import com.sign.dayschallenge.utils.Constants.Companion.PROF_NAME
import com.sign.dayschallenge.utils.Constants.Companion.SHARED_PREF
import com.sign.dayschallenge.viewmodel.MainFragmentViewModel
import kotlinx.android.synthetic.main.main_fragment_layout.*
import javax.inject.Inject
import javax.inject.Named
import android.view.animation.RotateAnimation
import android.widget.TextView
import androidx.preference.PreferenceManager
import com.sign.dayschallenge.utils.AnimUtil.Companion.rotateView
import kotlinx.coroutines.processNextEventInCurrentThread
import java.lang.IllegalArgumentException
import java.util.*


class MainFragment : Fragment(R.layout.main_fragment_layout), SharedPreferences.OnSharedPreferenceChangeListener {

    @Named("main_view_model")
    @Inject
    lateinit var mainViewModelFactory : ViewModelProvider.Factory
    private val viewModel : MainFragmentViewModel by viewModels { mainViewModelFactory  }
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var challengeAdapter : ChallengeAdapter

    private lateinit var tvQuotes : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appComponent = (requireActivity().application as MyApplication).appComponent
        appComponent.inject(this)


        initSharedPreferences()
        initStartScreen()
        setRandomQuote()
        setRecyclerView()
        setChallengeItems()

        tvQuotes = view.findViewById(R.id.tv_quote)
        if (PreferenceManager.getDefaultSharedPreferences(requireContext()).getBoolean("show_quotes", false)){
            tvQuotes.visibility = View.VISIBLE
        } else {
            tvQuotes.visibility = View.INVISIBLE
        }

        fab_button.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_createChallengeFragment)
        }

        ll_new_challenge.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_createChallengeFragment)
        }

        iv_settings.setOnClickListener {
            rotateView(60f, iv_settings)
            findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
        }

        if (!sharedPreferences.getBoolean("show_quotes",false)){
            tv_quote.visibility = View.GONE
        }

        challengeAdapter.setOnItemClickListener { challenge, listenerNumber ->
            if (listenerNumber==0){
                val action = MainFragmentDirections.actionMainFragmentToChallengeFragment(challenge)
                findNavController().navigate(action)
            } else if (listenerNumber==1){
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage(getString(R.string.delete_challenge))
                    .setTitle("${getString(R.string.delete)} ${challenge.title}")
                    .setNegativeButton(getString(R.string.no_str)) { _, _ -> }
                    .setPositiveButton(getString(R.string.yes_str)) { dialog, which ->
                        challengeAdapter.differ.submitList(viewModel.deleteItem(challenge))
                    }.show()
            }
        }
    }

    private fun setRandomQuote(){
        val quotesList = resources.getStringArray(R.array.quotes)
        val quotesSize = quotesList.size
        val randNumber = (0..quotesSize-1).random()
        tv_quote.text = "\"${quotesList[randNumber]}\""
    }

    private fun initSharedPreferences(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    private fun initStartScreen(){
        val str = getString(R.string.welcome_back)+"\n"+sharedPreferences.getString(PROF_NAME,"User")
        tv_welcome.text = str
    }

    private fun setRecyclerView(){
        challengeAdapter = ChallengeAdapter()
        rec_view_challenge.apply {
            adapter = challengeAdapter
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL,false)

        }
    }

    private fun setChallengeItems(){
        viewModel.allDataFromDB.observe(viewLifecycleOwner, Observer {
            if (it.size!=0){
                ll_new_challenge.visibility = View.GONE
                challengeAdapter.differ.submitList(it)
            } else{
                ll_new_challenge.visibility = View.VISIBLE
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences?, key: String?) {
        when(key){
            "app_language"->{
                when(prefs?.getString("app_language","en")){
                    "en"-> (activity as MainActivity).setLocale(resources.getStringArray(R.array.locales_array)[0])
                    "es"-> (activity as MainActivity).setLocale(resources.getStringArray(R.array.locales_array)[1])
                    "uk"-> (activity as MainActivity).setLocale(resources.getStringArray(R.array.locales_array)[2])
                    "ru"-> (activity as MainActivity).setLocale(resources.getStringArray(R.array.locales_array)[3])
                }
            }
            "show_quotes"->{
                if (prefs?.getBoolean("show_quotes", false) == true){
                    tvQuotes.visibility = View.VISIBLE
                } else{
                    tvQuotes.visibility = View.INVISIBLE
                }
            }
        }
    }
}