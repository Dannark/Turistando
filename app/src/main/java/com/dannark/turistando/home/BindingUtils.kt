package com.dannark.turistando.home

import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dannark.turistando.database.Place
import com.dannark.turistando.database.Post

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
        setImageResource(item.img!!)
    }
}

@BindingAdapter("postsLikesFormatted")
fun TextView.postsLikesFormatted(item: Post){
    item?.let {
        text = "Curtido por outras ${item.likes} pessoas"
    }
}

@BindingAdapter("postImage")
fun ImageView.setPostImage(item: Post){
    item?.let {
        setImageResource(item.img!!)
    }
}
