package com.dannark.turistando.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.databinding.BindingAdapter
import com.dannark.turistando.database.Place
import com.dannark.turistando.database.Post
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Runnable
import java.net.URL

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
        val uiScope = CoroutineScope(Dispatchers.Main)
        uiScope.launch {
            setImageBitmap(updateImageFromInternet(item.img!!))

        }
    }
}

@BindingAdapter("postsLikesFormatted")
fun TextView.postsLikesFormatted(item: Post){
    item?.let {
        text = "Curtido por outras ${item.likes} pessoas"
    }
}

@BindingAdapter("setImageFromInternet")
fun ImageView.setImageFromInternet(item: Post){
    item?.let {
        val uiScope = CoroutineScope(Dispatchers.Main)
        uiScope.launch {
            setImageBitmap(updateImageFromInternet(item.img!!))
        }
    }
}
private suspend fun updateImageFromInternet(imgURL: String): Bitmap?{
    return withContext(Dispatchers.IO){
        var url = URL(imgURL)
        var connection = url.openConnection()
        var image = null

        try {
            return@withContext BitmapFactory.decodeStream(connection.getInputStream());
        }catch (ioe: IOException) {
            Log.e("BindingUtils","Fail loading image from internet, check your" +
                    " connectivity")
        }
        image
    }
}

