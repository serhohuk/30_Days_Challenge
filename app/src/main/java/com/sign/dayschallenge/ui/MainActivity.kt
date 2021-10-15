package com.sign.dayschallenge.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.sign.dayschallenge.R
import com.sign.dayschallenge.utils.Constants.Companion.PROF_NAME
import com.sign.dayschallenge.utils.Constants.Companion.SHARED_PREF

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE)

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
}