package com.dannark.turistando

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.work.*
import com.dannark.turistando.databinding.ActivityMainBinding
import com.dannark.turistando.work.RefreshDataWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val applicationScope = CoroutineScope(Dispatchers.IO)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        showSystemUI()

        delayedInit()
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

    private fun showSystemUI() {
        // window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE) //white font
        // window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) //dark font
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
    }

}