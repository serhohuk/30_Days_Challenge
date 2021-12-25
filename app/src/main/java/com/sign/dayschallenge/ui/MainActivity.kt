package com.sign.dayschallenge.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.sign.dayschallenge.R
import com.sign.dayschallenge.application.MyApplication
import com.sign.dayschallenge.di.AppComponent
import com.sign.dayschallenge.utils.Constants.Companion.PROF_NAME
import com.sign.dayschallenge.utils.Constants.Companion.SHARED_PREF
import com.sign.dayschallenge.viewmodel.MainAppViewModel
import com.sign.dayschallenge.viewmodel.MainFragmentViewModel
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity() {

    @Named("main_app_view_model")
    @Inject
    lateinit var mainViewModelFactory : ViewModelProvider.Factory
    private val viewModelApp : MainAppViewModel by viewModels { mainViewModelFactory  }

    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)

        sharedPreferences = getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE)
        updateApplicationData()
        setNavigationGraph()
    }

    private fun setNavigationGraph(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.my_nav_graph)
        val startValue = isFirstStart()
        navGraph.startDestination =
            if (startValue){
                R.id.profileFragment
            }
            else{
                R.id.mainFragment
            }
        navController.graph = navGraph
    }

    private fun isFirstStart() : Boolean{
        if (sharedPreferences.getString(PROF_NAME, "User")=="User"){
            return true
        }
        return false
    }

    private fun updateApplicationData(){
        viewModelApp.allDataFromDB.observe(this, {
            for (item in it){
                viewModelApp.updateDayState(item.id,viewModelApp.getNewDayStateList(item))
            }
        })
    }
}