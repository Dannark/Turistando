package com.dannark.turistando.home

import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dannark.turistando.database.Place

@BindingAdapter("placeContryFormatted")
fun TextView.setPlaceContryFormatted(item: Place){
    item?.let {
        text = item.contry
    }
}

@BindingAdapter("placeCityString")
fun TextView.setPlaceCityString(item: Place){
    item?.let {
        text = item.city
    }
}

@BindingAdapter("placeImage")
fun ImageButton.setPlaceImage(item: Place){
    item?.let {
        setImageResource(item.backgroundImg!!)
    }
}