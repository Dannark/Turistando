package com.dannark.turistando.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dannark.turistando.R
import com.dannark.turistando.domain.Post
import java.util.concurrent.TimeUnit

@BindingAdapter("postsLikesFormatted")
fun TextView.postsLikesFormatted(item: Post){
    item.let {
        if(item.likes > 0){
            visibility = View.VISIBLE
            text = "${item.likes}"
        }
        else{
            visibility = View.GONE
        }
    }
}

@BindingAdapter("timePastFormatted")
fun TextView.timePastFormatted(time: Long){
    time.let{
        val diffInMillisec = System.currentTimeMillis() - it

        val diffInDays: Long = TimeUnit.MILLISECONDS.toDays(diffInMillisec)
        val diffInHours: Long = TimeUnit.MILLISECONDS.toHours(diffInMillisec)
        val diffInMin: Long = TimeUnit.MILLISECONDS.toMinutes(diffInMillisec)
        val diffInSec: Long = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec)

        val postTime = when{
            diffInSec < 60 -> String.format(resources.getString(R.string.posted_time_s), diffInSec)
            diffInMin < 60 -> String.format(resources.getString(R.string.posted_time_m), diffInMin)
            diffInHours < 24 -> String.format(resources.getString(R.string.posted_time_h), diffInHours)
            diffInDays < 31 -> String.format(resources.getString(R.string.posted_time_d), diffInDays)
            else -> "-"
        }

        text = postTime

    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String){
    imgUrl.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}
