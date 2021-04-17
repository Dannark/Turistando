package com.dannark.turistando.home

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dannark.turistando.R
import com.dannark.turistando.domain.Place
import com.dannark.turistando.domain.Post

@BindingAdapter("placeContryFormatted")
fun TextView.setPlaceContryFormatted(item: Place){
    item.let {
        text = item.contry
    }
}

@BindingAdapter("placeCityString")
fun TextView.setPlaceCityString(item: Place){
    item.let {
        text = item.city
    }
}

@BindingAdapter("postsLikesFormatted")
fun TextView.postsLikesFormatted(item: Post){
    item.let {
        text = "Curtido por outras ${item.likes} pessoas"
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView:ImageView, imgUrl: String){
    imgUrl.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}
