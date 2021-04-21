package com.dannark.turistando.viewmodels

import androidx.lifecycle.ViewModel
import com.dannark.turistando.R

class MainViewModel : ViewModel() {

    fun getFragmentIdOnClick(buttonId: Int): Int{
        return when (buttonId) {
            R.id.homeMenu -> R.id.homeMenu
            R.id.postsMenu -> R.id.postsMenu
            R.id.profileMenu -> -1
            R.id.searchMenu -> -1
            R.id.notificationMenu -> -1
            else -> -1
        }
    }

}