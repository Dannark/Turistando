package com.dannark.turistando.database

import com.dannark.turistando.R

data class Place(
        var id: Long = 0,
        var city: String? = "Poços de Caldas",
        var contry: String? = "Brazil - MG",
        var backgroundImg: Int? = R.drawable.landscape1
)