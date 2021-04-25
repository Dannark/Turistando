package com.dannark.turistando.domain

import androidx.annotation.DrawableRes

data class Suggestion(
        val id: String,
        @DrawableRes val iconResId: Int,
        val title: String,
        val subtitle: String,
        val date: Long
)
