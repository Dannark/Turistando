package com.dannark.turistando.util

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit


private val PUNCTUATION = listOf(", ", "; ", ": ", " ")

/**
 * Truncate long text with a preference for word boundaries and without trailing punctuation.
 */
fun String.smartTruncate(length: Int): String {
    val words = split(" ")
    var added = 0
    var hasMore = false
    val builder = StringBuilder()
    for (word in words) {
        if (builder.length > length) {
            hasMore = true
            break
        }
        builder.append(word)
        builder.append(" ")
        added += 1
    }

    PUNCTUATION.map {
        if (builder.endsWith(it)) {
            builder.replace(builder.length - it.length, builder.length, "")
        }
    }

    if (hasMore) {
        builder.append("...")
    }
    return builder.toString()
}

fun Boolean.toInt() = if(this) 1 else 0
fun Int.toBoolean() = this==1

fun Bitmap.toByteArray(): ByteArray? {
    val outputStream = ByteArrayOutputStream()
    this.compress(CompressFormat.PNG, 0, outputStream)
    return outputStream.toByteArray()
}
fun ByteArray.toBitmap(): Bitmap?{
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}

/*
 * (convert big string id such as "ASD464G6KVJ5RC45A" to a int id 1106
 */
fun String.toLongSum() = this.map { it.toInt() }.sum().toLong()

fun isConnectedToInternet(application: Application): Boolean{
    val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    val isConnected: Boolean = activeNetwork?.isConnected == true

    return isConnected
}

fun getTimeArrayDiff(past:Long): TimeObject {
    val diffInMillisec = System.currentTimeMillis() - past

    return TimeObject(
            TimeUnit.MILLISECONDS.toDays(diffInMillisec),
            TimeUnit.MILLISECONDS.toHours(diffInMillisec),
            TimeUnit.MILLISECONDS.toMinutes(diffInMillisec),
            TimeUnit.MILLISECONDS.toSeconds(diffInMillisec)
    )
}

data class TimeObject (
    val days: Long,
    val hours: Long,
    val min: Long,
    val sec: Long,
)