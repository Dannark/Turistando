package com.dannark.turistando

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.work.*
import com.dannark.turistando.databinding.ActivityMainBinding
import com.dannark.turistando.repository.userpref.DefaultUserPreferencesRepository
import com.dannark.turistando.repository.userpref.FirstTimeSelection
import com.dannark.turistando.viewmodels.MainViewModel
import com.dannark.turistando.work.RefreshDataWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private val applicationScope = CoroutineScope(Dispatchers.IO)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModelFactory =  MainViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.viewModel = viewModel

        val navController = Navigation.findNavController(this, R.id.myNavHostFragment)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        viewModel.checkFirstTimeEnabled().observe(this){ s ->
            setupMenu(s)
        }

        delayedInit()
    }

    private fun setupMenu(selection: FirstTimeSelection) {
        binding.bottomNavigation.isVisible = when (selection) {
            FirstTimeSelection.TRUE -> false
            else -> true
        }
    }

    private fun delayedInit() {
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        setRequiresDeviceIdle(true)
                    }
                }.build()

        applicationScope.launch {
            val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWork>(1, TimeUnit.DAYS)
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance().enqueueUniquePeriodicWork(
                    RefreshDataWork.WORK_NAME,
                    ExistingPeriodicWorkPolicy.KEEP,
                    repeatingRequest
            )
        }
    }

    fun setLightStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    fun setDarkStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.statusBarColor)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

}