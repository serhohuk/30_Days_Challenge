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
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.sign.dayschallenge.R
import com.sign.dayschallenge.application.MyApplication
import com.sign.dayschallenge.di.AppComponent
import com.sign.dayschallenge.utils.Constants.Companion.PROF_NAME
import com.sign.dayschallenge.utils.Constants.Companion.SHARED_PREF
import com.sign.dayschallenge.viewmodel.MainAppViewModel
import com.sign.dayschallenge.viewmodel.MainFragmentViewModel
import javax.inject.Inject
import javax.inject.Named
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources

import android.util.DisplayMetrics
import java.util.*


class MainActivity : AppCompatActivity() {

    @Named("main_app_view_model")
    @Inject
    lateinit var mainViewModelFactory : ViewModelProvider.Factory
    val viewModelApp : MainAppViewModel by viewModels { mainViewModelFactory  }

    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAnalytics = Firebase.analytics
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

    fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(this, MainActivity::class.java)
        startActivity(refresh)
        finish()
    }

    private fun updateApplicationData(){
        viewModelApp.allDataFromDB.observe(this, {
            for (item in it){
                viewModelApp.updateDayState(item.id,viewModelApp.getNewDayStateList(item))
            }
        })
    }
}