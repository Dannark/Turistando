package com.dannark.turistando

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.work.*
import com.dannark.turistando.databinding.ActivityMainBinding
import com.dannark.turistando.ui.posts.PostFragmentDirections
import com.dannark.turistando.viewmodels.MainViewModel
import com.dannark.turistando.work.RefreshDataWork
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private val applicationScope = CoroutineScope(Dispatchers.IO)

    private lateinit var binding: ActivityMainBinding

    private var currentFragment = -1

    val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.myNavHostFragment)
                ?.childFragmentManager?.fragments?.first()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
//            item.onNavDestinationSelected(
//                    findNavController(R.id.myNavHostFragment)
//            )
            when (item.itemId) {
                R.id.postFragment -> nagivateToPosts()
                R.id.exploreFragment -> navigateToExplore()
                R.id.searchFragment -> nagivateToSearch()
                else -> false
            }
        }

        val navController = Navigation.findNavController(this, R.id.myNavHostFragment)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        delayedInit()
    }

    private fun nagivateToPosts(): Boolean{
        if(currentFragment == R.id.postFragment) return false
        currentFragment = R.id.postFragment
        //applyAnimationFadeInSharedZ()
        val directions = PostFragmentDirections.actionGlobalPostsFragment()
        findNavController(R.id.myNavHostFragment).navigate(directions)
        return true
    }

    private fun navigateToExplore() :Boolean{
        if(currentFragment == R.id.exploreFragment) return false
        currentFragment = R.id.exploreFragment
        applyAnimationFadeInSharedZ()
        //val directions = ExploreFragmentDirections.actionGlobalExploreFragment()
        //findNavController(R.id.myNavHostFragment).navigate(directions)
        return true
    }

    private fun nagivateToSearch(): Boolean{
        if(currentFragment == R.id.searchFragment) return false
        currentFragment = R.id.searchFragment
        applyAnimationFadeInSharedZ()
        //val directions = SearchFragmentDirections.actionGlobalSearchFragment()
        //findNavController(R.id.myNavHostFragment).navigate(directions)
        return true
    }

    private fun applyAnimationFadeInSharedZ(){
        currentNavigationFragment?.apply {
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
                duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            }
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
                duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            }
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