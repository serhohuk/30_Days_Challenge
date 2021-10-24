package com.sign.dayschallenge.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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

class MainFragment : Fragment(R.layout.main_fragment_layout) {

    @Named("main_view_model")
    @Inject
    lateinit var mainViewModelFactory : ViewModelProvider.Factory
    private val viewModel : MainFragmentViewModel by viewModels { mainViewModelFactory  }
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var challengeAdapter : ChallengeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appComponent = (requireActivity().application as MyApplication).appComponent
        appComponent.inject(this)
        initSharedPreferences()
        initStartScreen()
        setRandomQuote()
        setRecyclerView()
        setChallengeItems()

        fab_button.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_createChallengeFragment)
        }
    }

    private fun setRandomQuote(){
        val quotesList = resources.getStringArray(R.array.quotes)
        val quotesSize = quotesList.size
        val randNumber = (0..quotesSize-1).random()
        tv_quote.text = quotesList[randNumber]
    }

    private fun initSharedPreferences(){
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE)
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
            challengeAdapter.differ.submitList(it)
        })
    }
}