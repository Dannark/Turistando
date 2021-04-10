package com.dannark.turistando

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.dannark.turistando.databinding.ActivityMainBinding
import com.dannark.turistando.home.HomeFragment
import com.dannark.turistando.home.HomeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        showSystemUI()

        drawerLayout = binding.drawerLayout
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupWithNavController(binding.navView, navController)

    }

    private fun showSystemUI() {
        // window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE) //white font
        // window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) //dark font
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    fun openDrawer() {
        binding.drawerLayout.openDrawer(Gravity.START)
    }
}